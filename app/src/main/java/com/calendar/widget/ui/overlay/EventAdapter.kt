package com.calendar.widget.ui.overlay

import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.calendar.widget.data.model.Event
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

/**
 * RecyclerView adapter for displaying calendar events grouped by day.
 * Supports both day headers and event items with proper Material styling.
 */
@Singleton
class EventAdapter @Inject constructor() : 
    ListAdapter<EventAdapter.ListItem, RecyclerView.ViewHolder>(DiffCallback()) {

    /**
     * Sealed class representing items in the list.
     */
    sealed class ListItem {
        data class DayHeader(val date: Date) : ListItem()
        data class EventItem(val event: Event) : ListItem()
    }

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_EVENT = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ListItem.DayHeader -> VIEW_TYPE_HEADER
            is ListItem.EventItem -> VIEW_TYPE_EVENT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_HEADER -> {
                val view = DayHeaderView(parent.context)
                view.layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                HeaderViewHolder(view)
            }
            VIEW_TYPE_EVENT -> {
                val view = EventItemView(parent.context)
                view.layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                EventViewHolder(view)
            }
            else -> throw IllegalArgumentException("Unknown view type: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is ListItem.DayHeader -> (holder as HeaderViewHolder).bind(item.date, hasEventsBelow(position))
            is ListItem.EventItem -> (holder as EventViewHolder).bind(item.event)
        }
    }

    private fun hasEventsBelow(headerPosition: Int): Boolean {
        for (i in headerPosition + 1 until itemCount) {
            when (getItem(i)) {
                is ListItem.EventItem -> return true
                is ListItem.DayHeader -> return false
            }
        }
        return false
    }

    class HeaderViewHolder(private val view: DayHeaderView) : RecyclerView.ViewHolder(view) {
        fun bind(date: Date, hasEvents: Boolean) {
            view.bind(date, hasEvents)
        }
    }

    class EventViewHolder(private val view: EventItemView) : RecyclerView.ViewHolder(view) {
        fun bind(event: Event) {
            view.bind(event)
            view.setOnClickListener {
                // Handle event click - could open event details
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<ListItem>() {
        override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
            return when {
                oldItem is ListItem.DayHeader && newItem is ListItem.DayHeader ->
                    oldItem.date == newItem.date
                oldItem is ListItem.EventItem && newItem is ListItem.EventItem ->
                    oldItem.event.id == newItem.event.id
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
            return oldItem == newItem
        }
    }

    /**
     * Submits a list of events, automatically grouping them by day with headers.
     */
    fun submitEvents(events: List<Event>) {
        val items = groupEventsByDay(events)
        submitList(items)
    }

    private fun groupEventsByDay(events: List<Event>): List<ListItem> {
        val calendar = java.util.Calendar.getInstance()
        val items = mutableListOf<ListItem>()
        var currentDay: Int? = null

        events.sortedBy { it.startTime }.forEach { event ->
            calendar.timeInMillis = event.startTime
            val day = calendar.get(java.util.Calendar.DAY_OF_YEAR)

            if (day != currentDay) {
                items.add(ListItem.DayHeader(Date(event.startTime)))
                currentDay = day
            }

            items.add(ListItem.EventItem(event))
        }

        return items
    }
}
