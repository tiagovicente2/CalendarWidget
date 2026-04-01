package com.calendar.widget.sync

import android.content.Context
import com.calendar.widget.util.Logger
import com.calendar.widget.data.model.Event
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.calendar.Calendar
import com.google.api.services.calendar.CalendarScopes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Handles synchronization of Google Calendar events.
 */
@Singleton
class GoogleCalendarSync @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private var calendarService: Calendar? = null

    /**
     * Sets up Google account credentials for API access.
     * Must be called before syncCalendar() after user signs in.
     * 
     * @param accountName The Google account email address
     * @return true if account was found and credential set, false otherwise
     */
    fun setCredential(accountName: String): Boolean {
        val accountManager = android.accounts.AccountManager.get(context)
        val allAccounts = accountManager.accounts
        Logger.d("GoogleCalendarSync", "All visible accounts: ${allAccounts.joinToString { "${it.name} (${it.type})" }}")
        
        val account = allAccounts.find { it.name == accountName && it.type == "com.google" }
        
        if (account == null) {
            Logger.e("GoogleCalendarSync", "Account $accountName not found in device AccountManager. " +
                "User needs to add this account to the device first.")
            calendarService = null
            return false
        }

        val credential = GoogleAccountCredential.usingOAuth2(
            context,
            listOf(CalendarScopes.CALENDAR_READONLY)
        ).setSelectedAccount(account)

        calendarService = Calendar.Builder(
            NetHttpTransport(),
            GsonFactory.getDefaultInstance(),
            credential
        )
            .setApplicationName("Calendar Widget")
            .build()
            
        return true
    }

    /**
     * Gets the list of calendars available to the user.
     */
    suspend fun getCalendarList(): List<com.google.api.services.calendar.model.CalendarListEntry> = kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
        val service = calendarService ?: return@withContext emptyList()
        try {
            val calendarList = service.calendarList().list().execute()
            calendarList.items ?: emptyList()
        } catch (e: com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException) {
            throw e
        } catch (e: Exception) {
            Logger.e("GoogleCalendarSync", "Error fetching calendar list", e)
            emptyList()
        }
    }

    /**
     * Syncs events from a specific Google Calendar.
     * 
     * @param calendarId The ID of the calendar to sync (defaults to "primary")
     * @param calendarColor The background color of the calendar to use as fallback
     * @return List of events, or empty list if not authenticated or on error
     */
    suspend fun syncCalendar(calendarId: String = "primary", calendarColor: Int? = null): List<Event> = kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
        Logger.d("GoogleCalendarSync", "syncCalendar ($calendarId) running on thread: ${Thread.currentThread().name}")
        val service = calendarService ?: run {
            Logger.e("GoogleCalendarSync", "Calendar service is null")
            return@withContext emptyList<Event>()
        }
        
        try {
            val calendar = java.util.Calendar.getInstance()
            calendar.set(java.util.Calendar.HOUR_OF_DAY, 0)
            calendar.set(java.util.Calendar.MINUTE, 0)
            calendar.set(java.util.Calendar.SECOND, 0)
            calendar.set(java.util.Calendar.MILLISECOND, 0)
            
            val startOfToday = com.google.api.client.util.DateTime(calendar.timeInMillis)
            Logger.d("GoogleCalendarSync", "Fetching events from $calendarId since $startOfToday")
            val events = service.events().list(calendarId)
                .setTimeMin(startOfToday)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute()

            Logger.d("GoogleCalendarSync", "Fetched ${events.items?.size ?: 0} events from $calendarId")
            events.items?.mapNotNull { googleEvent ->
                mapGoogleEventToDomain(googleEvent, calendarColor)
            } ?: emptyList()
        } catch (e: com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException) {
            Logger.e("GoogleCalendarSync", "UserRecoverableAuthIOException: needs user intervention", e)
            throw e
        } catch (e: Exception) {
            Logger.e("GoogleCalendarSync", "Error syncing Google Calendar $calendarId", e)
            emptyList()
        }
    }

    private fun mapGoogleEventToDomain(googleEvent: com.google.api.services.calendar.model.Event, fallbackColor: Int?): Event? {
        val id = googleEvent.id ?: return null
        val title = googleEvent.summary ?: "Untitled"
        
        val start = googleEvent.start
        val end = googleEvent.end
        
        val isAllDay = start.date != null
        val startTime = if (isAllDay) {
            start.date?.value ?: return null
        } else {
            start.dateTime?.value ?: return null
        }
        
        val endTime = if (isAllDay) {
            end.date?.value ?: (startTime + 86400000)
        } else {
            end.dateTime?.value ?: (startTime + 3600000)
        }

        val color = if (googleEvent.colorId != null) {
            mapGoogleColor(googleEvent.colorId)
        } else {
            fallbackColor ?: -769226 // Default blue
        }

        return Event(
            id = "google_$id",
            title = title,
            startTime = startTime,
            endTime = endTime,
            location = googleEvent.location,
            description = googleEvent.description,
            isAllDay = isAllDay,
            calendarSource = Event.SOURCE_GOOGLE,
            color = color
        )
    }

    private fun mapGoogleColor(colorId: String?): Int {
        val colorMap = mapOf(
            "1" to -769226,    // Lavender
            "2" to -101594,    // Sage
            "3" to -32830,     // Grape
            "4" to -16749408,  // Flamingo
            "5" to -59580,     // Banana
            "6" to -2611984,   // Tangerine
            "7" to -16738680,  // Peacock
            "8" to -65536,     // Graphite
            "9" to -13369549,  // Blueberry
            "10" to -3355444,  // Basil
            "11" to -12000284  // Tomato
        )
        return colorMap[colorId] ?: -769226 // Default blue
    }
}
