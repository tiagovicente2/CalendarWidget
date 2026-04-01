package com.calendar.widget.ui.overlay

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.calendar.widget.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Custom view for displaying a day header in the event list.
 * Shows the day name and number (e.g., "Mon 1").
 */
class DayHeaderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val dayNumberText: TextView
    private val dayStatusText: TextView
    private val dayFormat = SimpleDateFormat("EEE", Locale.getDefault())
    private val dateFormat = SimpleDateFormat("d", Locale.getDefault())

    init {
        LayoutInflater.from(context).inflate(R.layout.item_day_header, this, true)
        orientation = HORIZONTAL
        
        dayNumberText = findViewById(R.id.day_number)
        dayStatusText = findViewById(R.id.day_status)
    }

    /**
     * Binds the day header with a date and event presence status.
     * 
     * @param date The date to display
     * @param hasEvents Whether there are events on this day (controls empty state text visibility)
     */
    fun bind(date: Date, hasEvents: Boolean) {
        val dayName = dayFormat.format(date)
        val dayNumber = dateFormat.format(date)
        dayNumberText.text = "$dayName $dayNumber"
        
        dayStatusText.text = if (hasEvents) {
            ""
        } else {
            context.getString(R.string.no_events)
        }
        dayStatusText.visibility = if (hasEvents) GONE else VISIBLE
    }
}
