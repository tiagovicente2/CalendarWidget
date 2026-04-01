package com.calendar.widget.data.remote.dto

/**
 * DTO representing a Google Calendar event list response.
 */
data class GoogleEventListDto(
    val items: List<GoogleEventDto>?
)

/**
 * DTO representing a single Google Calendar event.
 */
data class GoogleEventDto(
    val id: String,
    val summary: String?,
    val location: String?,
    val description: String?,
    val start: EventTimeDto?,
    val end: EventTimeDto?,
    val colorId: String?
)

/**
 * DTO representing event time (start or end).
 */
data class EventTimeDto(
    val date: String?,      // For all-day events (format: YYYY-MM-DD)
    val dateTime: String?,  // For timed events (ISO 8601 format)
    val timeZone: String?
)
