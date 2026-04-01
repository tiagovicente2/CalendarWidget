package com.calendar.widget.ui.detail

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.calendar.widget.databinding.ActivityEventDetailsBinding
import com.google.android.material.color.MaterialColors
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Activity displaying detailed information for a single calendar event.
 * Shows basic info: title, date/time, location, description.
 */
@AndroidEntryPoint
class EventDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEventDetailsBinding

    companion object {
        const val EXTRA_EVENT_ID = "event_id"
        const val EXTRA_EVENT_TITLE = "event_title"
        const val EXTRA_EVENT_START = "event_start"
        const val EXTRA_EVENT_END = "event_end"
        const val EXTRA_EVENT_LOCATION = "event_location"
        const val EXTRA_EVENT_DESCRIPTION = "event_description"
        const val EXTRA_EVENT_ALL_DAY = "event_all_day"
        const val EXTRA_EVENT_COLOR = "event_color"
        const val EXTRA_EVENT_SOURCE = "event_source"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        displayEventDetails()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun displayEventDetails() {
        val title = intent.getStringExtra(EXTRA_EVENT_TITLE) ?: "Untitled"
        val startTime = intent.getLongExtra(EXTRA_EVENT_START, 0)
        val endTime = intent.getLongExtra(EXTRA_EVENT_END, 0)
        val location = intent.getStringExtra(EXTRA_EVENT_LOCATION)
        val description = intent.getStringExtra(EXTRA_EVENT_DESCRIPTION)
        val isAllDay = intent.getBooleanExtra(EXTRA_EVENT_ALL_DAY, false)
        val color = intent.getIntExtra(EXTRA_EVENT_COLOR, -769226)
        val source = intent.getStringExtra(EXTRA_EVENT_SOURCE) ?: "unknown"

        // Set title
        supportActionBar?.title = title
        binding.eventTitle.text = title

        // Set color bar
        binding.eventColorBar.setBackgroundColor(color)

        // Set date/time
        val dateTimeText = formatDateTime(startTime, endTime, isAllDay)
        binding.eventDateTime.text = dateTimeText

        // Set location
        if (location != null) {
            binding.eventLocation.text = location
            binding.eventLocation.visibility = android.view.View.VISIBLE
            binding.locationLabel.visibility = android.view.View.VISIBLE
        } else {
            binding.eventLocation.visibility = android.view.View.GONE
            binding.locationLabel.visibility = android.view.View.GONE
        }

        // Set description
        if (description != null) {
            binding.eventDescription.text = description
            binding.eventDescription.visibility = android.view.View.VISIBLE
            binding.descriptionLabel.visibility = android.view.View.VISIBLE
        } else {
            binding.eventDescription.visibility = android.view.View.GONE
            binding.descriptionLabel.visibility = android.view.View.GONE
        }

        // Set calendar source
        val sourceText = when {
            source == "google" -> "Google Calendar"
            source.startsWith("ical:") -> "iCal Subscription"
            else -> "Unknown Source"
        }
        binding.eventSource.text = sourceText
    }

    private fun formatDateTime(startTime: Long, endTime: Long, isAllDay: Boolean): String {
        val dateFormat = SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.getDefault())
        val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())

        return if (isAllDay) {
            val startDate = dateFormat.format(Date(startTime))
            "All day · $startDate"
        } else {
            val startDate = dateFormat.format(Date(startTime))
            val startTimeStr = timeFormat.format(Date(startTime))
            val endTimeStr = timeFormat.format(Date(endTime))
            "$startDate · $startTimeStr - $endTimeStr"
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
