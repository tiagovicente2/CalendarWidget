package com.calendar.widget.ui.detail;

/**
 * Activity displaying detailed information for a single calendar event.
 * Shows basic info: title, date/time, location, description.
 */
@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0005\u001a\u00020\u0006H\u0002J \u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\rH\u0002J\u0012\u0010\u000e\u001a\u00020\u00062\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0014J\u0010\u0010\u0011\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\u0006H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0016"}, d2 = {"Lcom/calendar/widget/ui/detail/EventDetailsActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/calendar/widget/databinding/ActivityEventDetailsBinding;", "displayEventDetails", "", "formatDateTime", "", "startTime", "", "endTime", "isAllDay", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onOptionsItemSelected", "item", "Landroid/view/MenuItem;", "setupToolbar", "Companion", "app_debug"})
public final class EventDetailsActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.calendar.widget.databinding.ActivityEventDetailsBinding binding;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_EVENT_ID = "event_id";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_EVENT_TITLE = "event_title";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_EVENT_START = "event_start";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_EVENT_END = "event_end";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_EVENT_LOCATION = "event_location";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_EVENT_DESCRIPTION = "event_description";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_EVENT_ALL_DAY = "event_all_day";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_EVENT_COLOR = "event_color";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_EVENT_SOURCE = "event_source";
    @org.jetbrains.annotations.NotNull()
    public static final com.calendar.widget.ui.detail.EventDetailsActivity.Companion Companion = null;
    
    public EventDetailsActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setupToolbar() {
    }
    
    private final void displayEventDetails() {
    }
    
    private final java.lang.String formatDateTime(long startTime, long endTime, boolean isAllDay) {
        return null;
    }
    
    @java.lang.Override()
    public boolean onOptionsItemSelected(@org.jetbrains.annotations.NotNull()
    android.view.MenuItem item) {
        return false;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\t\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2 = {"Lcom/calendar/widget/ui/detail/EventDetailsActivity$Companion;", "", "()V", "EXTRA_EVENT_ALL_DAY", "", "EXTRA_EVENT_COLOR", "EXTRA_EVENT_DESCRIPTION", "EXTRA_EVENT_END", "EXTRA_EVENT_ID", "EXTRA_EVENT_LOCATION", "EXTRA_EVENT_SOURCE", "EXTRA_EVENT_START", "EXTRA_EVENT_TITLE", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}