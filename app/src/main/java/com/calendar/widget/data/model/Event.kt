package com.calendar.widget.data.model

/**
 * Domain model representing a calendar event.
 * Used throughout the app UI layer and business logic.
 */
data class Event(
    val id: String,
    val title: String,
    val startTime: Long,
    val endTime: Long,
    val location: String?,
    val description: String?,
    val isAllDay: Boolean,
    val calendarSource: String,
    val color: Int
) {
    companion object {
        const val SOURCE_GOOGLE = "google"
        
        /**
         * Creates an iCal source identifier from a calendar URL.
         */
        fun createIcalSource(url: String) = "ical:$url"
    }

    /**
     * Checks if this event is from Google Calendar.
     */
    fun isGoogleCalendar(): Boolean = calendarSource == SOURCE_GOOGLE

    /**
     * Checks if this event is from an iCal URL subscription.
     */
    fun isIcal(): Boolean = calendarSource.startsWith("ical:")
}
