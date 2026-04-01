package com.calendar.widget.ui.overlay

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.calendar.widget.R
import com.calendar.widget.data.model.Event
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Custom view for displaying a single calendar event item.
 * Shows colored bar, title, time, and location.
 */
class EventItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val titleText: TextView
    private val detailsText: TextView
    private val colorBar: View
    private val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    init {
        LayoutInflater.from(context).inflate(R.layout.item_event, this, true)
        orientation = HORIZONTAL
        
        titleText = findViewById(R.id.event_title)
        detailsText = findViewById(R.id.event_details)
        colorBar = findViewById(R.id.event_color_bar)
    }

    /**
     * Binds the event item with event data.
     * 
     * @param event The event to display
     */
    fun bind(event: Event) {
        titleText.text = event.title
        colorBar.setBackgroundColor(event.color)

        val details = buildEventDetails(event)
        detailsText.text = details
    }

    private fun buildEventDetails(event: Event): String {
        return if (event.isAllDay) {
            "All day"
        } else {
            val startTime = timeFormat.format(Date(event.startTime))
            val endTime = timeFormat.format(Date(event.endTime))
            val locationText = event.location?.let { " · $it" } ?: ""
            "$startTime-$endTime$locationText"
        }
    }
}
