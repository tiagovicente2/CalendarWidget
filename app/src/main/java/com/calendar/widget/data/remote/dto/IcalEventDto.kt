package com.calendar.widget.data.remote.dto

/**
 * DTO representing a parsed iCal event.
 */
data class IcalEventDto(
    val uid: String,
    val summary: String,
    val location: String?,
    val description: String?,
    val startTime: Long,
    val endTime: Long,
    val isAllDay: Boolean
)
