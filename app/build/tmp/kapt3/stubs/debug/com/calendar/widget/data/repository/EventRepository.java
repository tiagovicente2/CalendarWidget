package com.calendar.widget.data.repository;

/**
 * Repository for managing calendar events.
 * Provides clean API for data operations with automatic entity/domain mapping.
 */
@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0005\u001a\u00020\u0006H\u0086@\u00a2\u0006\u0002\u0010\u0007J\u0016\u0010\b\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010\u000bJ\u0016\u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u000eH\u0086@\u00a2\u0006\u0002\u0010\u000fJ\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011H\u0086@\u00a2\u0006\u0002\u0010\u0007J$\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00120\u00112\u0006\u0010\u0014\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u000eH\u0086@\u00a2\u0006\u0002\u0010\u0016J\u001a\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\u00110\u00182\u0006\u0010\u0014\u001a\u00020\u000eJ\u0016\u0010\u0019\u001a\u00020\u00062\u0006\u0010\u001a\u001a\u00020\u0012H\u0086@\u00a2\u0006\u0002\u0010\u001bJ\u001c\u0010\u001c\u001a\u00020\u00062\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011H\u0086@\u00a2\u0006\u0002\u0010\u001eJ\f\u0010\u001f\u001a\u00020\u0012*\u00020 H\u0002J\f\u0010!\u001a\u00020 *\u00020\u0012H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\""}, d2 = {"Lcom/calendar/widget/data/repository/EventRepository;", "", "eventDao", "Lcom/calendar/widget/data/local/database/EventDao;", "(Lcom/calendar/widget/data/local/database/EventDao;)V", "clearGoogleCalendarEvents", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "clearIcalEvents", "url", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "clearOldEvents", "cutoffDate", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllEvents", "", "Lcom/calendar/widget/data/model/Event;", "getEventsForDateRange", "startDate", "endDate", "(JJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getEventsFromDate", "Lkotlinx/coroutines/flow/Flow;", "saveEvent", "event", "(Lcom/calendar/widget/data/model/Event;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveEvents", "events", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "toDomainModel", "Lcom/calendar/widget/data/local/entity/EventEntity;", "toEntity", "app_debug"})
public final class EventRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.calendar.widget.data.local.database.EventDao eventDao = null;
    
    @javax.inject.Inject()
    public EventRepository(@org.jetbrains.annotations.NotNull()
    com.calendar.widget.data.local.database.EventDao eventDao) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getEventsForDateRange(long startDate, long endDate, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.calendar.widget.data.model.Event>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getAllEvents(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.calendar.widget.data.model.Event>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.calendar.widget.data.model.Event>> getEventsFromDate(long startDate) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object saveEvents(@org.jetbrains.annotations.NotNull()
    java.util.List<com.calendar.widget.data.model.Event> events, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object saveEvent(@org.jetbrains.annotations.NotNull()
    com.calendar.widget.data.model.Event event, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object clearGoogleCalendarEvents(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object clearIcalEvents(@org.jetbrains.annotations.NotNull()
    java.lang.String url, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object clearOldEvents(long cutoffDate, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final com.calendar.widget.data.model.Event toDomainModel(com.calendar.widget.data.local.entity.EventEntity $this$toDomainModel) {
        return null;
    }
    
    private final com.calendar.widget.data.local.entity.EventEntity toEntity(com.calendar.widget.data.model.Event $this$toEntity) {
        return null;
    }
}