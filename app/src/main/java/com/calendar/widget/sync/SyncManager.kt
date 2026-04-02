package com.calendar.widget.sync

import android.content.Context
import android.content.SharedPreferences
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.calendar.widget.util.Logger
import com.calendar.widget.data.local.prefs.PreferenceKeys
import com.calendar.widget.data.model.Event
import com.calendar.widget.data.repository.EventRepository
import com.calendar.widget.service.SyncWorker
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Central manager for calendar synchronization operations.
 * Coordinates Google Calendar and iCal sync, manages scheduling, and tracks sync state.
 */
@Singleton
class SyncManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val eventRepository: EventRepository,
    private val googleCalendarSync: GoogleCalendarSync,
    private val icalSync: IcalSync,
    private val sharedPreferences: SharedPreferences
) {
    companion object {
        private const val DEFAULT_SYNC_INTERVAL = 6
        private const val SMART_SYNC_THRESHOLD_MS = 3600000L // 1 hour
    }

    private val workManager = WorkManager.getInstance(context)

    /**
     * Performs a full sync of all calendar sources.
     * Clears old data, fetches fresh data, and stores in local database.
     */
    suspend fun performFullSync() = kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
        val allEvents = mutableListOf<Event>()

        // Sync Google Calendar
        val accountEmail = sharedPreferences.getString(PreferenceKeys.GOOGLE_ACCOUNT, null)
        Logger.d("SyncManager", "Performing full sync. Account: $accountEmail")
        if (accountEmail != null) {
            val credentialSet = googleCalendarSync.setCredential(accountEmail)
            if (!credentialSet) {
                Logger.w("SyncManager", "Failed to set credentials for $accountEmail")
                return@withContext
            }
            
            val selectedCalendars = getSelectedGoogleCalendars()
            val calendarsToSyncIds = if (selectedCalendars.isEmpty()) listOf("primary") else selectedCalendars
            
            // Fetch calendar list to get colors
            val availableCalendars = try {
                googleCalendarSync.getCalendarList()
            } catch (e: Exception) {
                emptyList()
            }
            
            // Fetch all events first
            val allNewGoogleEvents = mutableListOf<Event>()
            calendarsToSyncIds.forEach { calendarId ->
                Logger.d("SyncManager", "Syncing Google Calendar: $calendarId")
                
                val calendarEntry = availableCalendars.find { it.id == calendarId }
                val calendarColor = calendarEntry?.let { entry ->
                    try {
                        android.graphics.Color.parseColor(entry.backgroundColor)
                    } catch (e: Exception) {
                        null
                    }
                }
                
                val googleEvents = googleCalendarSync.syncCalendar(calendarId, calendarColor)
                Logger.d("SyncManager", "Fetched ${googleEvents.size} events from $calendarId")
                allNewGoogleEvents.addAll(googleEvents)
            }
            
            // Replace in DB once
            eventRepository.replaceEventsForSource(Event.SOURCE_GOOGLE, allNewGoogleEvents)
            allEvents.addAll(allNewGoogleEvents)
        } else {
            Logger.w("SyncManager", "No Google account connected, skipping Google sync")
        }

        // Sync iCal calendars
        val icalUrls = getIcalUrls()
        icalUrls.forEach { url ->
            val icalEvents = icalSync.syncCalendar(url)
            if (icalEvents.isNotEmpty()) {
                eventRepository.replaceEventsForSource(Event.createIcalSource(url), icalEvents)
                allEvents.addAll(icalEvents)
            }
        }

        // Cleanup old events (older than 30 days)
        val thirtyDaysAgo = System.currentTimeMillis() - (30L * 24L * 60L * 60L * 1000L)
        eventRepository.clearOldEvents(thirtyDaysAgo)

        // Update last sync timestamp
        sharedPreferences.edit()
            .putLong(PreferenceKeys.LAST_SYNC_TIMESTAMP, System.currentTimeMillis())
            .apply()

        // Notify widget to update
        com.calendar.widget.widget.CalendarAppWidgetProvider.sendRefreshBroadcast(context)
    }

    /**
     * Checks if a smart sync should be performed based on time since last sync.
     * Returns true if more than 1 hour has passed since last sync.
     */
    fun shouldPerformSmartSync(): Boolean {
        val lastSync = sharedPreferences.getLong(PreferenceKeys.LAST_SYNC_TIMESTAMP, 0)
        val timeSinceLastSync = System.currentTimeMillis() - lastSync
        return timeSinceLastSync > SMART_SYNC_THRESHOLD_MS
    }

    /**
     * Gets the timestamp of the last successful sync.
     * Returns 0 if no sync has been performed yet.
     */
    fun getLastSyncTimestamp(): Long {
        return sharedPreferences.getLong(PreferenceKeys.LAST_SYNC_TIMESTAMP, 0)
    }

    /**
     * Gets the list of available Google Calendars.
     */
    suspend fun getGoogleCalendarList(): List<com.google.api.services.calendar.model.CalendarListEntry> {
        val accountEmail = sharedPreferences.getString(PreferenceKeys.GOOGLE_ACCOUNT, null)
        if (accountEmail != null) {
            val credentialSet = googleCalendarSync.setCredential(accountEmail)
            if (!credentialSet) {
                return emptyList()
            }
            return googleCalendarSync.getCalendarList()
        }
        return emptyList()
    }

    /**
     * Clears all local data, including events and preferences.
     * Used when signing out.
     */
    suspend fun clearAllData() {
        eventRepository.clearAllEvents()
        sharedPreferences.edit()
            .remove(PreferenceKeys.GOOGLE_ACCOUNT)
            .remove(PreferenceKeys.SELECTED_GOOGLE_CALENDARS)
            .apply()
        
        // Notify widget to update (to show empty state)
        com.calendar.widget.widget.CalendarAppWidgetProvider.sendRefreshBroadcast(context)
    }

    /**
     * Schedules periodic background sync using WorkManager.
     * Default interval is 6 hours.
     */
    fun schedulePeriodicSync() {
        val intervalHours = sharedPreferences.getInt(PreferenceKeys.SYNC_INTERVAL_HOURS, DEFAULT_SYNC_INTERVAL)
        
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val syncWorkRequest = PeriodicWorkRequestBuilder<SyncWorker>(
            intervalHours.toLong(), TimeUnit.HOURS
        )
            .setConstraints(constraints)
            .build()

        workManager.enqueueUniquePeriodicWork(
            SyncWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            syncWorkRequest
        )
    }

    /**
     * Cancels the scheduled periodic sync.
     */
    fun cancelPeriodicSync() {
        workManager.cancelUniqueWork(SyncWorker.WORK_NAME)
    }

    /**
     * Gets the list of iCal URLs.
     */
    fun getIcalUrls(): List<String> {
        return sharedPreferences.getStringSet(PreferenceKeys.ICAL_URLS, emptySet())?.toList() ?: emptyList()
    }

    /**
     * Adds an iCal URL subscription.
     */
    fun addIcalUrl(url: String) {
        val currentUrls = getIcalUrls().toMutableSet()
        currentUrls.add(url)
        sharedPreferences.edit()
            .putStringSet(PreferenceKeys.ICAL_URLS, currentUrls)
            .apply()
    }

    /**
     * Removes an iCal URL subscription.
     */
    fun removeIcalUrl(url: String) {
        val currentUrls = getIcalUrls().toMutableSet()
        currentUrls.remove(url)
        sharedPreferences.edit()
            .putStringSet(PreferenceKeys.ICAL_URLS, currentUrls)
            .apply()
    }

    /**
     * Gets the list of selected Google Calendar IDs.
     */
    fun getSelectedGoogleCalendars(): List<String> {
        return sharedPreferences.getStringSet(PreferenceKeys.SELECTED_GOOGLE_CALENDARS, emptySet())?.toList() ?: emptyList()
    }

    /**
     * Updates the list of selected Google Calendar IDs.
     */
    fun setSelectedGoogleCalendars(calendarIds: List<String>) {
        sharedPreferences.edit()
            .putStringSet(PreferenceKeys.SELECTED_GOOGLE_CALENDARS, calendarIds.toSet())
            .apply()
    }
}
