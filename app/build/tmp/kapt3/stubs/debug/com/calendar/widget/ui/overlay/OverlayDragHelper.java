package com.calendar.widget.ui.overlay;

/**
 * Helper class for handling drag operations on the floating overlay window.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u000e\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0005J\u0010\u0010\u0014\u001a\u00020\r2\u0006\u0010\u0015\u001a\u00020\u0016H\u0002R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2 = {"Lcom/calendar/widget/ui/overlay/OverlayDragHelper;", "", "windowManager", "Landroid/view/WindowManager;", "overlayView", "Landroid/view/View;", "params", "Landroid/view/WindowManager$LayoutParams;", "(Landroid/view/WindowManager;Landroid/view/View;Landroid/view/WindowManager$LayoutParams;)V", "initialX", "", "initialY", "isDragging", "", "touchX", "", "touchY", "attachToHandle", "", "handleView", "handleTouchEvent", "event", "Landroid/view/MotionEvent;", "app_debug"})
public final class OverlayDragHelper {
    @org.jetbrains.annotations.NotNull()
    private final android.view.WindowManager windowManager = null;
    @org.jetbrains.annotations.NotNull()
    private final android.view.View overlayView = null;
    @org.jetbrains.annotations.NotNull()
    private final android.view.WindowManager.LayoutParams params = null;
    private int initialX = 0;
    private int initialY = 0;
    private float touchX = 0.0F;
    private float touchY = 0.0F;
    private boolean isDragging = false;
    
    public OverlayDragHelper(@org.jetbrains.annotations.NotNull()
    android.view.WindowManager windowManager, @org.jetbrains.annotations.NotNull()
    android.view.View overlayView, @org.jetbrains.annotations.NotNull()
    android.view.WindowManager.LayoutParams params) {
        super();
    }
    
    /**
     * Attaches drag handling to a specific view (typically a drag handle).
     */
    public final void attachToHandle(@org.jetbrains.annotations.NotNull()
    android.view.View handleView) {
    }
    
    private final boolean handleTouchEvent(android.view.MotionEvent event) {
        return false;
    }
}