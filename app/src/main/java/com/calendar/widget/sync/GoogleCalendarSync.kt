package com.calendar.widget.sync

import android.content.Context
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
     */
    fun setCredential(accountName: String) {
        val credential = GoogleAccountCredential.usingOAuth2(
            context,
            listOf(CalendarScopes.CALENDAR_READONLY)
        ).setSelectedAccountName(accountName)

        calendarService = Calendar.Builder(
            NetHttpTransport(),
            GsonFactory.getDefaultInstance(),
            credential
        )
            .setApplicationName("Calendar Widget")
            .build()
    }

    /**
     * Syncs events from the primary Google Calendar.
     * 
     * @return List of events, or empty list if not authenticated or on error
     */
    suspend fun syncCalendar(): List<Event> {
        val service = calendarService ?: return emptyList()
        
        return try {
            val now = com.google.api.client.util.DateTime(System.currentTimeMillis())
            val events = service.events().list("primary")
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute()

            events.items?.mapNotNull { googleEvent ->
                mapGoogleEventToDomain(googleEvent)
            } ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun mapGoogleEventToDomain(googleEvent: com.google.api.services.calendar.model.Event): Event? {
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

        val color = mapGoogleColor(googleEvent.colorId)

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
