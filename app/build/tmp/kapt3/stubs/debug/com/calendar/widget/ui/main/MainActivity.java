package com.calendar.widget.ui.main;

/**
 * Main activity displaying the calendar event list.
 * Full-screen list view with grouped events by day.
 */
@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0013\u001a\u00020\u0014H\u0002J\u0012\u0010\u0015\u001a\u00020\u00142\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0014J\b\u0010\u0018\u001a\u00020\u0014H\u0002J\b\u0010\u0019\u001a\u00020\u0014H\u0002J\b\u0010\u001a\u001a\u00020\u0014H\u0002J\b\u0010\u001b\u001a\u00020\u0014H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u0007\u001a\u00020\b8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001e\u0010\r\u001a\u00020\u000e8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012\u00a8\u0006\u001c"}, d2 = {"Lcom/calendar/widget/ui/main/MainActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/calendar/widget/databinding/ActivityMainBinding;", "eventAdapter", "Lcom/calendar/widget/ui/overlay/EventAdapter;", "eventRepository", "Lcom/calendar/widget/data/repository/EventRepository;", "getEventRepository", "()Lcom/calendar/widget/data/repository/EventRepository;", "setEventRepository", "(Lcom/calendar/widget/data/repository/EventRepository;)V", "syncManager", "Lcom/calendar/widget/sync/SyncManager;", "getSyncManager", "()Lcom/calendar/widget/sync/SyncManager;", "setSyncManager", "(Lcom/calendar/widget/sync/SyncManager;)V", "observeEvents", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "openGoogleCalendar", "setupFab", "setupPullToRefresh", "setupRecyclerView", "app_debug"})
public final class MainActivity extends androidx.appcompat.app.AppCompatActivity {
    @javax.inject.Inject()
    public com.calendar.widget.data.repository.EventRepository eventRepository;
    @javax.inject.Inject()
    public com.calendar.widget.sync.SyncManager syncManager;
    private com.calendar.widget.databinding.ActivityMainBinding binding;
    private com.calendar.widget.ui.overlay.EventAdapter eventAdapter;
    
    public MainActivity() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.calendar.widget.data.repository.EventRepository getEventRepository() {
        return null;
    }
    
    public final void setEventRepository(@org.jetbrains.annotations.NotNull()
    com.calendar.widget.data.repository.EventRepository p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.calendar.widget.sync.SyncManager getSyncManager() {
        return null;
    }
    
    public final void setSyncManager(@org.jetbrains.annotations.NotNull()
    com.calendar.widget.sync.SyncManager p0) {
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setupRecyclerView() {
    }
    
    private final void setupPullToRefresh() {
    }
    
    private final void setupFab() {
    }
    
    private final void observeEvents() {
    }
    
    private final void openGoogleCalendar() {
    }
}