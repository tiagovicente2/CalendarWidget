package com.calendar.widget.sync;

/**
 * Central manager for calendar synchronization operations.
 * Coordinates Google Calendar and iCal sync, manages scheduling, and tracks sync state.
 */
@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0007\u0018\u0000 \u001e2\u00020\u0001:\u0001\u001eB1\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u00a2\u0006\u0002\u0010\fJ\u000e\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012J\u0006\u0010\u0013\u001a\u00020\u0010J\u000e\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00120\u0015H\u0002J\u0006\u0010\u0016\u001a\u00020\u0017J\u000e\u0010\u0018\u001a\u00020\u0010H\u0086@\u00a2\u0006\u0002\u0010\u0019J\u000e\u0010\u001a\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012J\u0006\u0010\u001b\u001a\u00020\u0010J\u0006\u0010\u001c\u001a\u00020\u001dR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001f"}, d2 = {"Lcom/calendar/widget/sync/SyncManager;", "", "context", "Landroid/content/Context;", "eventRepository", "Lcom/calendar/widget/data/repository/EventRepository;", "googleCalendarSync", "Lcom/calendar/widget/sync/GoogleCalendarSync;", "icalSync", "Lcom/calendar/widget/sync/IcalSync;", "sharedPreferences", "Landroid/content/SharedPreferences;", "(Landroid/content/Context;Lcom/calendar/widget/data/repository/EventRepository;Lcom/calendar/widget/sync/GoogleCalendarSync;Lcom/calendar/widget/sync/IcalSync;Landroid/content/SharedPreferences;)V", "workManager", "Landroidx/work/WorkManager;", "addIcalUrl", "", "url", "", "cancelPeriodicSync", "getIcalUrls", "", "getLastSyncTimestamp", "", "performFullSync", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "removeIcalUrl", "schedulePeriodicSync", "shouldPerformSmartSync", "", "Companion", "app_debug"})
public final class SyncManager {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final com.calendar.widget.data.repository.EventRepository eventRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.calendar.widget.sync.GoogleCalendarSync googleCalendarSync = null;
    @org.jetbrains.annotations.NotNull()
    private final com.calendar.widget.sync.IcalSync icalSync = null;
    @org.jetbrains.annotations.NotNull()
    private final android.content.SharedPreferences sharedPreferences = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String PREF_LAST_SYNC = "last_sync_timestamp";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String PREF_SYNC_INTERVAL = "sync_interval_hours";
    private static final int DEFAULT_SYNC_INTERVAL = 6;
    private static final long SMART_SYNC_THRESHOLD_MS = 3600000L;
    @org.jetbrains.annotations.NotNull()
    private final androidx.work.WorkManager workManager = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.calendar.widget.sync.SyncManager.Companion Companion = null;
    
    @javax.inject.Inject()
    public SyncManager(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.calendar.widget.data.repository.EventRepository eventRepository, @org.jetbrains.annotations.NotNull()
    com.calendar.widget.sync.GoogleCalendarSync googleCalendarSync, @org.jetbrains.annotations.NotNull()
    com.calendar.widget.sync.IcalSync icalSync, @org.jetbrains.annotations.NotNull()
    android.content.SharedPreferences sharedPreferences) {
        super();
    }
    
    /**
     * Performs a full sync of all calendar sources.
     * Clears old data, fetches fresh data, and stores in local database.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object performFullSync(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Checks if a smart sync should be performed based on time since last sync.
     * Returns true if more than 1 hour has passed since last sync.
     */
    public final boolean shouldPerformSmartSync() {
        return false;
    }
    
    /**
     * Gets the timestamp of the last successful sync.
     * Returns 0 if no sync has been performed yet.
     */
    public final long getLastSyncTimestamp() {
        return 0L;
    }
    
    /**
     * Schedules periodic background sync using WorkManager.
     * Default interval is 6 hours.
     */
    public final void schedulePeriodicSync() {
    }
    
    /**
     * Cancels the scheduled periodic sync.
     */
    public final void cancelPeriodicSync() {
    }
    
    private final java.util.List<java.lang.String> getIcalUrls() {
        return null;
    }
    
    /**
     * Adds an iCal URL subscription.
     */
    public final void addIcalUrl(@org.jetbrains.annotations.NotNull()
    java.lang.String url) {
    }
    
    /**
     * Removes an iCal URL subscription.
     */
    public final void removeIcalUrl(@org.jetbrains.annotations.NotNull()
    java.lang.String url) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2 = {"Lcom/calendar/widget/sync/SyncManager$Companion;", "", "()V", "DEFAULT_SYNC_INTERVAL", "", "PREF_LAST_SYNC", "", "PREF_SYNC_INTERVAL", "SMART_SYNC_THRESHOLD_MS", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}