package com.calendar.widget.data.local.database;

/**
 * Data Access Object for calendar events.
 * Provides CRUD operations and queries for events with coroutine support.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\bg\u0018\u00002\u00020\u0001J\u000e\u0010\u0002\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0005\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u0016\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\fJ\u0016\u0010\r\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u000fH\u00a7@\u00a2\u0006\u0002\u0010\u0010J\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00070\u0012H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0018\u0010\u0013\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u0014\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\fJ$\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00070\u00122\u0006\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u000fH\u00a7@\u00a2\u0006\u0002\u0010\u0018J\u001c\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00070\u00122\u0006\u0010\n\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\fJ\u001c\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00120\u001b2\u0006\u0010\u0016\u001a\u00020\u000fH\'J\u0016\u0010\u001c\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u001c\u0010\u001d\u001a\u00020\u00032\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00070\u0012H\u00a7@\u00a2\u0006\u0002\u0010\u001f\u00a8\u0006 "}, d2 = {"Lcom/calendar/widget/data/local/database/EventDao;", "", "deleteAllEvents", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteEvent", "event", "Lcom/calendar/widget/data/local/entity/EventEntity;", "(Lcom/calendar/widget/data/local/entity/EventEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteEventsBySource", "source", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteOldEvents", "cutoffDate", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllEvents", "", "getEventById", "eventId", "getEventsBetweenDates", "startDate", "endDate", "(JJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getEventsBySource", "getEventsFromDate", "Lkotlinx/coroutines/flow/Flow;", "insertEvent", "insertEvents", "events", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao()
public abstract interface EventDao {
    
    @androidx.room.Query(value = "SELECT * FROM events WHERE id = :eventId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getEventById(@org.jetbrains.annotations.NotNull()
    java.lang.String eventId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.calendar.widget.data.local.entity.EventEntity> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM events ORDER BY startTime ASC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAllEvents(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.calendar.widget.data.local.entity.EventEntity>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM events WHERE startTime >= :startDate AND startTime < :endDate ORDER BY startTime ASC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getEventsBetweenDates(long startDate, long endDate, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.calendar.widget.data.local.entity.EventEntity>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM events WHERE calendarSource = :source ORDER BY startTime ASC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getEventsBySource(@org.jetbrains.annotations.NotNull()
    java.lang.String source, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.calendar.widget.data.local.entity.EventEntity>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM events WHERE startTime >= :startDate ORDER BY startTime ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.calendar.widget.data.local.entity.EventEntity>> getEventsFromDate(long startDate);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertEvent(@org.jetbrains.annotations.NotNull()
    com.calendar.widget.data.local.entity.EventEntity event, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertEvents(@org.jetbrains.annotations.NotNull()
    java.util.List<com.calendar.widget.data.local.entity.EventEntity> events, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteEvent(@org.jetbrains.annotations.NotNull()
    com.calendar.widget.data.local.entity.EventEntity event, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM events WHERE calendarSource = :source")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteEventsBySource(@org.jetbrains.annotations.NotNull()
    java.lang.String source, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM events WHERE startTime < :cutoffDate")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteOldEvents(long cutoffDate, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM events")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteAllEvents(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}