package com.calendar.widget.sync

import android.content.Context
import android.content.SharedPreferences
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
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
        private const val PREF_LAST_SYNC = "last_sync_timestamp"
        private const val PREF_SYNC_INTERVAL = "sync_interval_hours"
        private const val DEFAULT_SYNC_INTERVAL = 6
        private const val SMART_SYNC_THRESHOLD_MS = 3600000L // 1 hour
    }

    private val workManager = WorkManager.getInstance(context)

    /**
     * Performs a full sync of all calendar sources.
     * Clears old data, fetches fresh data, and stores in local database.
     */
    suspend fun performFullSync() {
        val allEvents = mutableListOf<Event>()

        // Sync Google Calendar
        val googleEvents = googleCalendarSync.syncCalendar()
        if (googleEvents.isNotEmpty()) {
            eventRepository.clearGoogleCalendarEvents()
            eventRepository.saveEvents(googleEvents)
            allEvents.addAll(googleEvents)
        }

        // Sync iCal calendars
        val icalUrls = getIcalUrls()
        icalUrls.forEach { url ->
            val icalEvents = icalSync.syncCalendar(url)
            if (icalEvents.isNotEmpty()) {
                eventRepository.clearIcalEvents(url)
                eventRepository.saveEvents(icalEvents)
                allEvents.addAll(icalEvents)
            }
        }

        // Cleanup old events (older than 30 days)
        val thirtyDaysAgo = System.currentTimeMillis() - (30L * 24L * 60L * 60L * 1000L)
        eventRepository.clearOldEvents(thirtyDaysAgo)

        // Update last sync timestamp
        sharedPreferences.edit()
            .putLong(PREF_LAST_SYNC, System.currentTimeMillis())
            .apply()
    }

    /**
     * Checks if a smart sync should be performed based on time since last sync.
     * Returns true if more than 1 hour has passed since last sync.
     */
    fun shouldPerformSmartSync(): Boolean {
        val lastSync = sharedPreferences.getLong(PREF_LAST_SYNC, 0)
        val timeSinceLastSync = System.currentTimeMillis() - lastSync
        return timeSinceLastSync > SMART_SYNC_THRESHOLD_MS
    }

    /**
     * Gets the timestamp of the last successful sync.
     * Returns 0 if no sync has been performed yet.
     */
    fun getLastSyncTimestamp(): Long {
        return sharedPreferences.getLong(PREF_LAST_SYNC, 0)
    }

    /**
     * Schedules periodic background sync using WorkManager.
     * Default interval is 6 hours.
     */
    fun schedulePeriodicSync() {
        val intervalHours = sharedPreferences.getInt(PREF_SYNC_INTERVAL, DEFAULT_SYNC_INTERVAL)
        
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

    private fun getIcalUrls(): List<String> {
        return sharedPreferences.getStringSet("ical_urls", emptySet())?.toList() ?: emptyList()
    }

    /**
     * Adds an iCal URL subscription.
     */
    fun addIcalUrl(url: String) {
        val currentUrls = getIcalUrls().toMutableSet()
        currentUrls.add(url)
        sharedPreferences.edit()
            .putStringSet("ical_urls", currentUrls)
            .apply()
    }

    /**
     * Removes an iCal URL subscription.
     */
    fun removeIcalUrl(url: String) {
        val currentUrls = getIcalUrls().toMutableSet()
        currentUrls.remove(url)
        sharedPreferences.edit()
            .putStringSet("ical_urls", currentUrls)
            .apply()
    }
}
