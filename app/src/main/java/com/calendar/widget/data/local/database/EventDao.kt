package com.calendar.widget.data.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.calendar.widget.data.local.entity.EventEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for calendar events.
 * Provides CRUD operations and queries for events with coroutine support.
 */
@Dao
interface EventDao {
    
    @Query("SELECT * FROM events WHERE id = :eventId")
    suspend fun getEventById(eventId: String): EventEntity?

    @Query("SELECT * FROM events ORDER BY startTime ASC")
    suspend fun getAllEvents(): List<EventEntity>

    @Query("SELECT * FROM events WHERE startTime >= :startDate AND startTime < :endDate ORDER BY startTime ASC")
    suspend fun getEventsBetweenDates(startDate: Long, endDate: Long): List<EventEntity>

    @Query("SELECT * FROM events WHERE calendarSource = :source ORDER BY startTime ASC")
    suspend fun getEventsBySource(source: String): List<EventEntity>

    @Query("SELECT * FROM events WHERE startTime >= :startDate ORDER BY startTime ASC")
    fun getEventsFromDate(startDate: Long): Flow<List<EventEntity>>

    @androidx.room.Transaction
    suspend fun replaceEventsForSource(source: String, events: List<EventEntity>) {
        deleteEventsBySource(source)
        insertEvents(events)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: EventEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvents(events: List<EventEntity>)

    @Delete
    suspend fun deleteEvent(event: EventEntity)

    @Query("DELETE FROM events WHERE calendarSource = :source")
    suspend fun deleteEventsBySource(source: String)

    @Query("DELETE FROM events WHERE startTime < :cutoffDate")
    suspend fun deleteOldEvents(cutoffDate: Long)

    @Query("DELETE FROM events")
    suspend fun deleteAllEvents()
}
