package com.calendar.widget.widget

import android.content.Context
import android.content.Intent
import android.view.View
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

sealed class WidgetListItem {
    data class Header(val date: Date, val showMonth: Boolean) : WidgetListItem()
    data class EventItem(val event: Event) : WidgetListItem()
}

class CalendarWidgetFactory(private val context: Context) : RemoteViewsService.RemoteViewsFactory {

    private lateinit var eventRepository: EventRepository
    private var listItems: List<WidgetListItem> = emptyList()
    
    private val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    private val dayFormat = SimpleDateFormat("EEEE, MMM dd", Locale.getDefault())
    private val monthFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
    private val allDayFormat = SimpleDateFormat("MMM dd", Locale.getDefault())

    override fun onCreate() {
        val entryPoint = EntryPointAccessors.fromApplication(context, CalendarWidgetEntryPoint::class.java)
        eventRepository = entryPoint.eventRepository()
    }

    override fun onDataSetChanged() {
        Logger.d("CalendarWidgetFactory", "onDataSetChanged started")
        
        runBlocking {
            try {
                kotlinx.coroutines.withTimeout(5000L) {
                    val now = System.currentTimeMillis()
                    val thirtyDaysLater = now + 30L * 24 * 60 * 60 * 1000
                    val allEvents = eventRepository.getEventsForDateRange(now, thirtyDaysLater)
                    val sortedEvents = allEvents.filter { it.endTime > now }.sortedBy { it.startTime }
                    
                    val items = mutableListOf<WidgetListItem>()
                    var lastDateStr = ""
                    var lastMonthStr = ""
                    
                    val dayHeaderFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
                    val monthHeaderFormat = SimpleDateFormat("yyyyMM", Locale.getDefault())
                    
                    for (event in sortedEvents) {
                        val date = Date(event.startTime)
                        val dateStr = dayHeaderFormat.format(date)
                        val monthStr = monthHeaderFormat.format(date)
                        
                        if (dateStr != lastDateStr) {
                            val showMonth = monthStr != lastMonthStr
                            items.add(WidgetListItem.Header(date, showMonth))
                            lastDateStr = dateStr
                            lastMonthStr = monthStr
                        }
                        items.add(WidgetListItem.EventItem(event))
                    }
                    
                    listItems = items
                    Logger.d("CalendarWidgetFactory", "Fetched ${sortedEvents.size} events, created ${listItems.size} list items")
                }
            } catch (e: kotlinx.coroutines.TimeoutCancellationException) {
                Logger.e("CalendarWidgetFactory", "Timeout fetching events")
            } catch (e: Exception) {
                Logger.e("CalendarWidgetFactory", "Error in onDataSetChanged", e)
                listItems = emptyList()
            }
        }
    }

    override fun onDestroy() {
        listItems = emptyList()
    }

    override fun getCount(): Int = listItems.size

    override fun getViewAt(position: Int): RemoteViews {
        if (position < 0 || position >= listItems.size) {
            return RemoteViews(context.packageName, R.layout.widget_event_item)
        }

        return when (val item = listItems[position]) {
            is WidgetListItem.Header -> {
                val rv = RemoteViews(context.packageName, R.layout.widget_day_header)
                if (item.showMonth) {
                    rv.setViewVisibility(R.id.widget_header_month, View.VISIBLE)
                    rv.setTextViewText(R.id.widget_header_month, monthFormat.format(item.date))
                } else {
                    rv.setViewVisibility(R.id.widget_header_month, View.GONE)
                }
                rv.setTextViewText(R.id.widget_header_day, dayFormat.format(item.date))
                
                // Clicking header opens the app (MainActivity)
                val fillInIntent = Intent()
                rv.setOnClickFillInIntent(R.id.widget_header_root, fillInIntent)
                rv
            }
            is WidgetListItem.EventItem -> {
                val event = item.event
                val rv = RemoteViews(context.packageName, R.layout.widget_event_item)

                rv.setTextViewText(R.id.widget_event_title, event.title)
                
                val start = Date(event.startTime)
                val end = Date(event.endTime)
                val locationText = event.location?.let { " · $it" } ?: ""
                val timeStr = if (event.isAllDay) {
                    "All day$locationText"
                } else {
                    "${timeFormat.format(start)} - ${timeFormat.format(end)}$locationText"
                }
                rv.setTextViewText(R.id.widget_event_time, timeStr)
                
                if (event.color != 0) {
                    try {
                        rv.setInt(R.id.widget_event_color, "setColorFilter", event.color)
                    } catch (e: Exception) {
                        Logger.e("CalendarWidgetFactory", "Error setting color", e)
                    }
                }
                
                val fillInIntent = Intent().apply {
                    putExtra(com.calendar.widget.ui.detail.EventDetailsActivity.EXTRA_EVENT_ID, event.id)
                    putExtra(com.calendar.widget.ui.detail.EventDetailsActivity.EXTRA_EVENT_TITLE, event.title)
                    putExtra(com.calendar.widget.ui.detail.EventDetailsActivity.EXTRA_EVENT_START, event.startTime)
                    putExtra(com.calendar.widget.ui.detail.EventDetailsActivity.EXTRA_EVENT_END, event.endTime)
                    putExtra(com.calendar.widget.ui.detail.EventDetailsActivity.EXTRA_EVENT_LOCATION, event.location)
                    putExtra(com.calendar.widget.ui.detail.EventDetailsActivity.EXTRA_EVENT_DESCRIPTION, event.description)
                    putExtra(com.calendar.widget.ui.detail.EventDetailsActivity.EXTRA_EVENT_ALL_DAY, event.isAllDay)
                    putExtra(com.calendar.widget.ui.detail.EventDetailsActivity.EXTRA_EVENT_COLOR, event.color)
                    putExtra(com.calendar.widget.ui.detail.EventDetailsActivity.EXTRA_EVENT_SOURCE, event.calendarSource)
                }
                rv.setOnClickFillInIntent(R.id.widget_event_item_root, fillInIntent)
                rv
            }
        }
    }

    override fun getLoadingView(): RemoteViews? = null
    
    override fun getViewTypeCount(): Int = 2
    
    override fun getItemId(position: Int): Long = position.toLong()
    
    override fun hasStableIds(): Boolean = true
}