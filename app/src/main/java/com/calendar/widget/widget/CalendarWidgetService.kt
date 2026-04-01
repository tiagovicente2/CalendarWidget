package com.calendar.widget.widget

import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.calendar.widget.R
import com.calendar.widget.util.Logger
import com.calendar.widget.data.model.Event
import com.calendar.widget.data.repository.EventRepository
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CalendarWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
        return CalendarWidgetFactory(this.applicationContext)
    }
}

@EntryPoint
@InstallIn(SingletonComponent::class)
interface CalendarWidgetEntryPoint {
    fun eventRepository(): EventRepository
}

class CalendarWidgetFactory(private val context: Context) : RemoteViewsService.RemoteViewsFactory {

    private lateinit var eventRepository: EventRepository
    private var events: List<Event> = emptyList()
    private val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    private val dateFormat = SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault())
    private val allDayFormat = SimpleDateFormat("MMM dd", Locale.getDefault())

    override fun onCreate() {
        val entryPoint = EntryPointAccessors.fromApplication(context, CalendarWidgetEntryPoint::class.java)
        eventRepository = entryPoint.eventRepository()
    }

    override fun onDataSetChanged() {
        Logger.d("CalendarWidgetFactory", "onDataSetChanged started")
        
        // Use runBlocking with a timeout to prevent ANR
        runBlocking {
            try {
                kotlinx.coroutines.withTimeout(5000L) { // 5 second timeout
                    val now = System.currentTimeMillis()
                    val thirtyDaysLater = now + 30L * 24 * 60 * 60 * 1000
                    Logger.d("CalendarWidgetFactory", "Fetching events from repository")
                    val allEvents = eventRepository.getEventsForDateRange(now, thirtyDaysLater)
                    events = allEvents.filter { it.endTime > now }.sortedBy { it.startTime }
                    Logger.d("CalendarWidgetFactory", "Fetched ${events.size} events")
                }
            } catch (e: kotlinx.coroutines.TimeoutCancellationException) {
                Logger.e("CalendarWidgetFactory", "Timeout fetching events, showing stale data")
                // Keep existing events (don't clear them)
            } catch (e: Exception) {
                Logger.e("CalendarWidgetFactory", "Error in onDataSetChanged", e)
                events = emptyList()
            }
        }
    }

    override fun onDestroy() {
        events = emptyList()
    }

    override fun getCount(): Int {
        Logger.d("CalendarWidgetFactory", "getCount: ${events.size}")
        return events.size
    }

    override fun getViewAt(position: Int): RemoteViews {
        Logger.d("CalendarWidgetFactory", "getViewAt: $position")
        if (position < 0 || position >= events.size) {
            return RemoteViews(context.packageName, R.layout.widget_event_item)
        }

        val event = events[position]
        val rv = RemoteViews(context.packageName, R.layout.widget_event_item)

        rv.setTextViewText(R.id.widget_event_title, event.title)
        
        val start = Date(event.startTime)
        val timeStr = if (event.isAllDay) {
            "All day, ${allDayFormat.format(start)}"
        } else {
            dateFormat.format(start)
        }
        rv.setTextViewText(R.id.widget_event_time, timeStr)
        
        if (event.color != 0) {
            try {
                rv.setInt(R.id.widget_event_color, "setColorFilter", event.color)
            } catch (e: Exception) {
                com.calendar.widget.util.Logger.e("CalendarWidgetFactory", "Error setting color", e)
            }
        }
        
        // Setup fill-intent on the content layout
        val fillInIntent = Intent().apply {
            putExtra("event_id", event.id)
        }
        rv.setOnClickFillInIntent(R.id.widget_item_content, fillInIntent)

        return rv
    }

    override fun getLoadingView(): RemoteViews? = null
    override fun getViewTypeCount(): Int = 1
    override fun getItemId(position: Int): Long = position.toLong()
    override fun hasStableIds(): Boolean = true
}