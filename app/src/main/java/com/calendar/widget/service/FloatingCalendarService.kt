package com.calendar.widget.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.calendar.widget.R
import com.calendar.widget.data.repository.EventRepository
import com.calendar.widget.sync.SyncManager
import com.calendar.widget.ui.overlay.EventAdapter
import com.calendar.widget.ui.overlay.OverlayDragHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Foreground service that manages the floating calendar overlay widget.
 * Displays a draggable window with calendar events on top of other apps.
 */
@AndroidEntryPoint
class FloatingCalendarService : Service() {

    @Inject
    lateinit var eventAdapter: EventAdapter

    @Inject
    lateinit var eventRepository: EventRepository

    @Inject
    lateinit var syncManager: SyncManager

    private lateinit var windowManager: WindowManager
    private lateinit var overlayView: View
    private lateinit var dragHelper: OverlayDragHelper
    private val serviceScope = CoroutineScope(Dispatchers.Main + Job())
    
    private val notificationId = 1
    private val channelId = "calendar_widget_channel"

    companion object {
        const val ACTION_SHOW = "com.calendar.widget.SHOW"
        const val ACTION_HIDE = "com.calendar.widget.HIDE"
        const val ACTION_TOGGLE = "com.calendar.widget.TOGGLE"
        
        @Volatile
        var isRunning = false
            private set
    }

    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_SHOW -> showOverlay()
            ACTION_HIDE -> hideOverlay()
            ACTION_TOGGLE -> toggleOverlay()
        }
        
        if (!isRunning) {
            startForeground(notificationId, createNotification())
            isRunning = true
        }
        
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.service_notification_title)
            val descriptionText = getString(R.string.service_notification_text)
            val importance = NotificationManager.IMPORTANCE_LOW
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createNotification(): Notification {
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            Intent(this, Class.forName("com.calendar.widget.ui.config.ConfigActivity")),
            PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, channelId)
            .setContentTitle(getString(R.string.service_notification_title))
            .setContentText(getString(R.string.service_notification_text))
            .setSmallIcon(R.drawable.ic_add)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .build()
    }

    private fun showOverlay() {
        if (::overlayView.isInitialized && overlayView.isAttachedToWindow) {
            return // Already showing
        }

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) 
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY 
            else 
                WindowManager.LayoutParams.TYPE_PHONE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.START
            x = 100
            y = 200
        }

        overlayView = LayoutInflater.from(this)
            .inflate(R.layout.overlay_container, null)

        setupOverlayViews()
        setupDragHandling(params)
        observeEvents()
        checkSmartSync()

        try {
            windowManager.addView(overlayView, params)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setupOverlayViews() {
        val recyclerView = overlayView.findViewById<RecyclerView>(R.id.events_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = eventAdapter

        val fab = overlayView.findViewById<FloatingActionButton>(R.id.fab_add)
        fab.setOnClickListener {
            openGoogleCalendar()
        }
    }

    private fun setupDragHandling(params: WindowManager.LayoutParams) {
        val dragHandle = overlayView.findViewById<View>(R.id.drag_handle)
        dragHelper = OverlayDragHelper(windowManager, overlayView, params)
        dragHelper.attachToHandle(dragHandle)
    }

    private fun observeEvents() {
        serviceScope.launch {
            val today = System.currentTimeMillis()
            eventRepository.getEventsFromDate(today)
                .collectLatest { events ->
                    eventAdapter.submitEvents(events)
                }
        }
    }

    private fun checkSmartSync() {
        if (syncManager.shouldPerformSmartSync()) {
            serviceScope.launch {
                syncManager.performFullSync()
            }
        }
    }

    private fun hideOverlay() {
        if (::overlayView.isInitialized && overlayView.isAttachedToWindow) {
            windowManager.removeView(overlayView)
        }
    }

    private fun toggleOverlay() {
        if (::overlayView.isInitialized && overlayView.isAttachedToWindow) {
            hideOverlay()
        } else {
            showOverlay()
        }
    }

    private fun openGoogleCalendar() {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = android.net.Uri.parse("https://calendar.google.com")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        hideOverlay()
        isRunning = false
    }
}
