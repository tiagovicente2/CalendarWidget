package com.calendar.widget.ui.overlay

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.calendar.widget.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * Custom view for displaying a day header in the event list.
 * Shows the day name and number (e.g., "MON" over "24").
 */
class DayHeaderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val dayNameText: TextView
    private val dayNumberText: TextView
    private val dayStatusText: TextView
    private val dayNameFormat = SimpleDateFormat("EEE", Locale.getDefault())
    private val dayNumberFormat = SimpleDateFormat("d", Locale.getDefault())

    init {
        LayoutInflater.from(context).inflate(R.layout.item_day_header, this, true)
        orientation = HORIZONTAL
        
        dayNameText = findViewById(R.id.day_name)
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
        val dayName = dayNameFormat.format(date).uppercase()
        val dayNumber = dayNumberFormat.format(date)
        
        dayNameText.text = dayName
        dayNumberText.text = dayNumber
        
        // Highlight today
        val calendar = Calendar.getInstance()
        val today = Calendar.getInstance().apply { time = date }
        if (calendar.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR) &&
            calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR)) {
            dayNumberText.setTextColor(context.getColor(R.color.purple_500))
        }
        
        dayStatusText.visibility = if (hasEvents) GONE else VISIBLE
    }
}
