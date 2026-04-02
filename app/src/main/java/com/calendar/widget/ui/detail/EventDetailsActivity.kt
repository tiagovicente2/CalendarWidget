package com.calendar.widget.ui.detail

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.calendar.widget.data.model.Event
import com.calendar.widget.data.repository.EventRepository
import com.calendar.widget.databinding.ActivityEventDetailsBinding
import com.google.android.material.color.MaterialColors
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

/**
 * Activity displaying detailed information for a single calendar event.
 * Shows basic info: title, date/time, location, description.
 */
@AndroidEntryPoint
class EventDetailsActivity : AppCompatActivity() {

    @Inject
    lateinit var eventRepository: EventRepository

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
        loadEventDetails()
    }

    override fun onNewIntent(intent: android.content.Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        loadEventDetails()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        
        // Disable lift on scroll for consistency
        binding.toolbar.setBackgroundColor(MaterialColors.getColor(binding.toolbar, com.google.android.material.R.attr.colorSurface))
    }

    private fun loadEventDetails() {
        val eventId = intent.getStringExtra(EXTRA_EVENT_ID)
        
        if (eventId != null) {
            lifecycleScope.launch {
                val event = eventRepository.getEventById(eventId)
                if (event != null) {
                    displayEvent(event)
                } else {
                    // Fallback to extras if not found in DB (e.g. just synced out)
                    displayFromExtras()
                }
            }
        } else {
            displayFromExtras()
        }
    }

    private fun displayEvent(event: Event) {
        supportActionBar?.title = event.title
        binding.eventTitle.text = event.title
        binding.eventColorBar.setBackgroundColor(event.color)
        binding.eventDateTime.text = formatDateTime(event.startTime, event.endTime, event.isAllDay)

        if (!event.location.isNullOrEmpty()) {
            binding.eventLocation.text = event.location
            binding.eventLocation.visibility = android.view.View.VISIBLE
            binding.locationLabel.visibility = android.view.View.VISIBLE
            android.text.util.Linkify.addLinks(binding.eventLocation, android.text.util.Linkify.WEB_URLS)
            binding.eventLocation.movementMethod = android.text.method.LinkMovementMethod.getInstance()
        } else {
            binding.eventLocation.visibility = android.view.View.GONE
            binding.locationLabel.visibility = android.view.View.GONE
        }

        if (!event.description.isNullOrEmpty()) {
            binding.eventDescription.text = event.description
            binding.eventDescription.visibility = android.view.View.VISIBLE
            binding.descriptionLabel.visibility = android.view.View.VISIBLE
            android.text.util.Linkify.addLinks(binding.eventDescription, android.text.util.Linkify.WEB_URLS)
            binding.eventDescription.movementMethod = android.text.method.LinkMovementMethod.getInstance()
        } else {
            binding.eventDescription.visibility = android.view.View.GONE
            binding.descriptionLabel.visibility = android.view.View.GONE
        }

        binding.eventSource.text = formatSource(event.calendarSource)
    }

    private fun displayFromExtras() {
        val title = intent.getStringExtra(EXTRA_EVENT_TITLE) ?: "Untitled"
        val startTime = intent.getLongExtra(EXTRA_EVENT_START, 0)
        val endTime = intent.getLongExtra(EXTRA_EVENT_END, 0)
        val location = intent.getStringExtra(EXTRA_EVENT_LOCATION)
        val description = intent.getStringExtra(EXTRA_EVENT_DESCRIPTION)
        val isAllDay = intent.getBooleanExtra(EXTRA_EVENT_ALL_DAY, false)
        val color = intent.getIntExtra(EXTRA_EVENT_COLOR, -769226)
        val source = intent.getStringExtra(EXTRA_EVENT_SOURCE) ?: "unknown"

        supportActionBar?.title = title
        binding.eventTitle.text = title
        binding.eventColorBar.setBackgroundColor(color)
        binding.eventDateTime.text = formatDateTime(startTime, endTime, isAllDay)

        if (!location.isNullOrEmpty()) {
            binding.eventLocation.text = location
            binding.eventLocation.visibility = android.view.View.VISIBLE
            binding.locationLabel.visibility = android.view.View.VISIBLE
            android.text.util.Linkify.addLinks(binding.eventLocation, android.text.util.Linkify.WEB_URLS)
            binding.eventLocation.movementMethod = android.text.method.LinkMovementMethod.getInstance()
        } else {
            binding.eventLocation.visibility = android.view.View.GONE
            binding.locationLabel.visibility = android.view.View.GONE
        }

        if (!description.isNullOrEmpty()) {
            binding.eventDescription.text = description
            binding.eventDescription.visibility = android.view.View.VISIBLE
            binding.descriptionLabel.visibility = android.view.View.VISIBLE
            android.text.util.Linkify.addLinks(binding.eventDescription, android.text.util.Linkify.WEB_URLS)
            binding.eventDescription.movementMethod = android.text.method.LinkMovementMethod.getInstance()
        } else {
            binding.eventDescription.visibility = android.view.View.GONE
            binding.descriptionLabel.visibility = android.view.View.GONE
        }

        binding.eventSource.text = formatSource(source)
    }

    private fun formatSource(source: String): String {
        return when {
            source == "google" -> "Google Calendar"
            source.startsWith("ical:") -> "iCal Subscription"
            else -> "Unknown Source"
        }
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
