package com.calendar.widget.data.repository

import com.calendar.widget.data.local.database.EventDao
import com.calendar.widget.data.local.entity.EventEntity
import com.calendar.widget.data.model.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository for managing calendar events.
 * Provides clean API for data operations with automatic entity/domain mapping.
 */
@Singleton
class EventRepository @Inject constructor(
    private val eventDao: EventDao
) {

    suspend fun getEventsForDateRange(startDate: Long, endDate: Long): List<Event> {
        return eventDao.getEventsBetweenDates(startDate, endDate)
            .map { it.toDomainModel() }
    }

    suspend fun getAllEvents(): List<Event> {
        return eventDao.getAllEvents()
            .map { it.toDomainModel() }
    }

    fun getEventsFromDate(startDate: Long): Flow<List<Event>> {
        return eventDao.getEventsFromDate(startDate)
            .map { entities -> entities.map { it.toDomainModel() } }
    }

    suspend fun saveEvents(events: List<Event>) {
        val entities = events.map { it.toEntity() }
        eventDao.insertEvents(entities)
    }

    suspend fun saveEvent(event: Event) {
        eventDao.insertEvent(event.toEntity())
    }

    suspend fun clearGoogleCalendarEvents() {
        eventDao.deleteEventsBySource(Event.SOURCE_GOOGLE)
    }

    suspend fun replaceEventsForSource(source: String, newEvents: List<Event>) {
        val entities = newEvents.map { it.toEntity() }
        eventDao.replaceEventsForSource(source, entities)
    }

    suspend fun clearIcalEvents(url: String) {
        eventDao.deleteEventsBySource(Event.createIcalSource(url))
    }

    suspend fun clearOldEvents(cutoffDate: Long) {
        eventDao.deleteOldEvents(cutoffDate)
    }

    suspend fun clearAllEvents() {
        eventDao.deleteAllEvents()
    }

    private fun EventEntity.toDomainModel(): Event {
        return Event(
            id = id,
            title = title,
            startTime = startTime,
            endTime = endTime,
            location = location,
            description = description,
            isAllDay = isAllDay,
            calendarSource = calendarSource,
            color = color
        )
    }

    private fun Event.toEntity(): EventEntity {
        return EventEntity(
            id = id,
            title = title,
            startTime = startTime,
            endTime = endTime,
            location = location,
            description = description,
            isAllDay = isAllDay,
            calendarSource = calendarSource,
            color = color,
            lastUpdated = System.currentTimeMillis()
        )
    }
}
