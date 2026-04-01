package com.calendar.widget.service;

/**
 * Foreground service that manages the floating calendar overlay widget.
 * Displays a draggable window with calendar events on top of other apps.
 */
@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0007\u0018\u0000 82\u00020\u0001:\u00018B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010!\u001a\u00020\"H\u0002J\b\u0010#\u001a\u00020$H\u0002J\b\u0010%\u001a\u00020\"H\u0002J\b\u0010&\u001a\u00020\"H\u0002J\b\u0010\'\u001a\u00020\"H\u0002J\u0014\u0010(\u001a\u0004\u0018\u00010)2\b\u0010*\u001a\u0004\u0018\u00010+H\u0016J\b\u0010,\u001a\u00020\"H\u0016J\b\u0010-\u001a\u00020\"H\u0016J\"\u0010.\u001a\u00020\u00142\b\u0010*\u001a\u0004\u0018\u00010+2\u0006\u0010/\u001a\u00020\u00142\u0006\u00100\u001a\u00020\u0014H\u0016J\b\u00101\u001a\u00020\"H\u0002J\u0010\u00102\u001a\u00020\"2\u0006\u00103\u001a\u000204H\u0002J\b\u00105\u001a\u00020\"H\u0002J\b\u00106\u001a\u00020\"H\u0002J\b\u00107\u001a\u00020\"H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u0007\u001a\u00020\b8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001e\u0010\r\u001a\u00020\u000e8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u0019\u001a\u00020\u001a8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR\u000e\u0010\u001f\u001a\u00020 X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u00069"}, d2 = {"Lcom/calendar/widget/service/FloatingCalendarService;", "Landroid/app/Service;", "()V", "channelId", "", "dragHelper", "Lcom/calendar/widget/ui/overlay/OverlayDragHelper;", "eventAdapter", "Lcom/calendar/widget/ui/overlay/EventAdapter;", "getEventAdapter", "()Lcom/calendar/widget/ui/overlay/EventAdapter;", "setEventAdapter", "(Lcom/calendar/widget/ui/overlay/EventAdapter;)V", "eventRepository", "Lcom/calendar/widget/data/repository/EventRepository;", "getEventRepository", "()Lcom/calendar/widget/data/repository/EventRepository;", "setEventRepository", "(Lcom/calendar/widget/data/repository/EventRepository;)V", "notificationId", "", "overlayView", "Landroid/view/View;", "serviceScope", "Lkotlinx/coroutines/CoroutineScope;", "syncManager", "Lcom/calendar/widget/sync/SyncManager;", "getSyncManager", "()Lcom/calendar/widget/sync/SyncManager;", "setSyncManager", "(Lcom/calendar/widget/sync/SyncManager;)V", "windowManager", "Landroid/view/WindowManager;", "checkSmartSync", "", "createNotification", "Landroid/app/Notification;", "createNotificationChannel", "hideOverlay", "observeEvents", "onBind", "Landroid/os/IBinder;", "intent", "Landroid/content/Intent;", "onCreate", "onDestroy", "onStartCommand", "flags", "startId", "openGoogleCalendar", "setupDragHandling", "params", "Landroid/view/WindowManager$LayoutParams;", "setupOverlayViews", "showOverlay", "toggleOverlay", "Companion", "app_debug"})
public final class FloatingCalendarService extends android.app.Service {
    @javax.inject.Inject()
    public com.calendar.widget.ui.overlay.EventAdapter eventAdapter;
    @javax.inject.Inject()
    public com.calendar.widget.data.repository.EventRepository eventRepository;
    @javax.inject.Inject()
    public com.calendar.widget.sync.SyncManager syncManager;
    private android.view.WindowManager windowManager;
    private android.view.View overlayView;
    private com.calendar.widget.ui.overlay.OverlayDragHelper dragHelper;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineScope serviceScope = null;
    private final int notificationId = 1;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String channelId = "calendar_widget_channel";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ACTION_SHOW = "com.calendar.widget.SHOW";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ACTION_HIDE = "com.calendar.widget.HIDE";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ACTION_TOGGLE = "com.calendar.widget.TOGGLE";
    @kotlin.jvm.Volatile()
    private static volatile boolean isRunning = false;
    @org.jetbrains.annotations.NotNull()
    public static final com.calendar.widget.service.FloatingCalendarService.Companion Companion = null;
    
    public FloatingCalendarService() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.calendar.widget.ui.overlay.EventAdapter getEventAdapter() {
        return null;
    }
    
    public final void setEventAdapter(@org.jetbrains.annotations.NotNull()
    com.calendar.widget.ui.overlay.EventAdapter p0) {
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
    public void onCreate() {
    }
    
    @java.lang.Override()
    public int onStartCommand(@org.jetbrains.annotations.Nullable()
    android.content.Intent intent, int flags, int startId) {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public android.os.IBinder onBind(@org.jetbrains.annotations.Nullable()
    android.content.Intent intent) {
        return null;
    }
    
    private final void createNotificationChannel() {
    }
    
    private final android.app.Notification createNotification() {
        return null;
    }
    
    private final void showOverlay() {
    }
    
    private final void setupOverlayViews() {
    }
    
    private final void setupDragHandling(android.view.WindowManager.LayoutParams params) {
    }
    
    private final void observeEvents() {
    }
    
    private final void checkSmartSync() {
    }
    
    private final void hideOverlay() {
    }
    
    private final void toggleOverlay() {
    }
    
    private final void openGoogleCalendar() {
    }
    
    @java.lang.Override()
    public void onDestroy() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u001e\u0010\t\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\b@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u000b"}, d2 = {"Lcom/calendar/widget/service/FloatingCalendarService$Companion;", "", "()V", "ACTION_HIDE", "", "ACTION_SHOW", "ACTION_TOGGLE", "<set-?>", "", "isRunning", "()Z", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        public final boolean isRunning() {
            return false;
        }
    }
}