package com.calendar.widget.sync

import com.calendar.widget.data.model.Event
import com.calendar.widget.data.remote.api.IcalService
import com.calendar.widget.parser.IcalParser
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Handles synchronization of iCal calendar subscriptions.
 */
@Singleton
class IcalSync @Inject constructor(
    private val icalService: IcalService,
    private val icalParser: IcalParser
) {

    private val colorMap = mutableMapOf<String, Int>()
    private val calendarColors = listOf(
        -16738680, // Green
        -48060,    // Orange
        -4056997,  // Purple
        -16776961, // Blue
        -65536,    // Red
        -16711936  // Lime
    )

    /**
     * Syncs events from an iCal URL.
     * 
     * @param url The iCal calendar URL to fetch
     * @return List of events from the calendar, or empty list if fetch/parsing fails
     */
    suspend fun syncCalendar(url: String): List<Event> {
        return try {
            val response = icalService.fetchIcalCalendar(url)
            if (response.isSuccessful) {
                val icalData = response.body()?.string() ?: return emptyList()
                val dtos = icalParser.parse(icalData)
                
                dtos.map { dto ->
                    Event(
                        id = "ical_${dto.uid}",
                        title = dto.summary,
                        startTime = dto.startTime,
                        endTime = dto.endTime,
                        location = dto.location,
                        description = dto.description,
                        isAllDay = dto.isAllDay,
                        calendarSource = Event.createIcalSource(url),
                        color = getCalendarColor(url)
                    )
                }
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun getCalendarColor(url: String): Int {
        return colorMap.getOrPut(url) {
            calendarColors[colorMap.size % calendarColors.size]
        }
    }
}
