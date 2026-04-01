package com.calendar.widget.sync;

/**
 * Handles synchronization of iCal calendar subscriptions.
 */
@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\b\n\u0000\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0010\u0010\r\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\fH\u0002J\u001c\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\b2\u0006\u0010\u000e\u001a\u00020\fH\u0086@\u00a2\u0006\u0002\u0010\u0011R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\t0\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2 = {"Lcom/calendar/widget/sync/IcalSync;", "", "icalService", "Lcom/calendar/widget/data/remote/api/IcalService;", "icalParser", "Lcom/calendar/widget/parser/IcalParser;", "(Lcom/calendar/widget/data/remote/api/IcalService;Lcom/calendar/widget/parser/IcalParser;)V", "calendarColors", "", "", "colorMap", "", "", "getCalendarColor", "url", "syncCalendar", "Lcom/calendar/widget/data/model/Event;", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class IcalSync {
    @org.jetbrains.annotations.NotNull()
    private final com.calendar.widget.data.remote.api.IcalService icalService = null;
    @org.jetbrains.annotations.NotNull()
    private final com.calendar.widget.parser.IcalParser icalParser = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Map<java.lang.String, java.lang.Integer> colorMap = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.Integer> calendarColors = null;
    
    @javax.inject.Inject()
    public IcalSync(@org.jetbrains.annotations.NotNull()
    com.calendar.widget.data.remote.api.IcalService icalService, @org.jetbrains.annotations.NotNull()
    com.calendar.widget.parser.IcalParser icalParser) {
        super();
    }
    
    /**
     * Syncs events from an iCal URL.
     *
     * @param url The iCal calendar URL to fetch
     * @return List of events from the calendar, or empty list if fetch/parsing fails
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object syncCalendar(@org.jetbrains.annotations.NotNull()
    java.lang.String url, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.calendar.widget.data.model.Event>> $completion) {
        return null;
    }
    
    private final int getCalendarColor(java.lang.String url) {
        return 0;
    }
}