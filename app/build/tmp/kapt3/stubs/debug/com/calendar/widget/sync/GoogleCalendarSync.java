package com.calendar.widget.sync;

/**
 * Handles synchronization of Google Calendar events.
 */
@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0012\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0002J\u0012\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u000e\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\nJ\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\f0\u0013H\u0086@\u00a2\u0006\u0002\u0010\u0014R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"Lcom/calendar/widget/sync/GoogleCalendarSync;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "calendarService", "Lcom/google/api/services/calendar/Calendar;", "mapGoogleColor", "", "colorId", "", "mapGoogleEventToDomain", "Lcom/calendar/widget/data/model/Event;", "googleEvent", "Lcom/google/api/services/calendar/model/Event;", "setCredential", "", "accountName", "syncCalendar", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class GoogleCalendarSync {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.Nullable()
    private com.google.api.services.calendar.Calendar calendarService;
    
    @javax.inject.Inject()
    public GoogleCalendarSync(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    /**
     * Sets up Google account credentials for API access.
     * Must be called before syncCalendar() after user signs in.
     *
     * @param accountName The Google account email address
     */
    public final void setCredential(@org.jetbrains.annotations.NotNull()
    java.lang.String accountName) {
    }
    
    /**
     * Syncs events from the primary Google Calendar.
     *
     * @return List of events, or empty list if not authenticated or on error
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object syncCalendar(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.calendar.widget.data.model.Event>> $completion) {
        return null;
    }
    
    private final com.calendar.widget.data.model.Event mapGoogleEventToDomain(com.google.api.services.calendar.model.Event googleEvent) {
        return null;
    }
    
    private final int mapGoogleColor(java.lang.String colorId) {
        return 0;
    }
}