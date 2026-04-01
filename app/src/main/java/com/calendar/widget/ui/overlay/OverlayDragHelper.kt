package com.calendar.widget.ui.overlay

import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import kotlin.math.abs

/**
 * Helper class for handling drag operations on the floating overlay window.
 */
class OverlayDragHelper(
    private val windowManager: WindowManager,
    private val overlayView: View,
    private val params: WindowManager.LayoutParams
) {
    private var initialX = 0
    private var initialY = 0
    private var touchX = 0f
    private var touchY = 0f
    private var isDragging = false

    /**
     * Attaches drag handling to a specific view (typically a drag handle).
     */
    fun attachToHandle(handleView: View) {
        handleView.setOnTouchListener { _, event ->
            handleTouchEvent(event)
            true
        }
    }

    private fun handleTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                initialX = params.x
                initialY = params.y
                touchX = event.rawX
                touchY = event.rawY
                isDragging = false
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = event.rawX - touchX
                val dy = event.rawY - touchY

                if (abs(dx) > 10 || abs(dy) > 10) {
                    isDragging = true
                }

                if (isDragging) {
                    params.x = (initialX + dx).toInt()
                    params.y = (initialY + dy).toInt()
                    windowManager.updateViewLayout(overlayView, params)
                }
                return true
            }
            MotionEvent.ACTION_UP -> {
                return isDragging
            }
        }
        return false
    }
}
