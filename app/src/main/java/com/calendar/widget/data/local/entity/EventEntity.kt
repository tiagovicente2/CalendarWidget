package com.calendar.widget.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room entity representing a calendar event.
 * Stores both Google Calendar and iCal events with source attribution.
 */
@Entity(tableName = "events")
data class EventEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val startTime: Long,
    val endTime: Long,
    val location: String?,
    val description: String?,
    val isAllDay: Boolean,
    val calendarSource: String,
    val color: Int,
    val lastUpdated: Long
)
