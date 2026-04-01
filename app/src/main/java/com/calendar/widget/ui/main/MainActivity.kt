package com.calendar.widget.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.calendar.widget.R
import com.calendar.widget.data.repository.EventRepository
import com.calendar.widget.databinding.ActivityMainBinding
import com.calendar.widget.sync.SyncManager
import com.calendar.widget.ui.detail.EventDetailsActivity
import com.calendar.widget.ui.overlay.EventAdapter
import com.calendar.widget.ui.settings.SettingsActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Main activity displaying the calendar event list.
 * Full-screen list view with grouped events by day.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var eventRepository: EventRepository

    @Inject
    lateinit var syncManager: SyncManager

    private lateinit var binding: ActivityMainBinding
    private lateinit var eventAdapter: EventAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupPullToRefresh()
        setupFab()
        setupEmptyState()
        observeEvents()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupRecyclerView() {
        binding.eventsRecyclerView.layoutManager = LinearLayoutManager(this)
        
        // Create adapter with click handler
        val adapter = EventAdapter().apply {
            setOnEventClickListener { event ->
                val intent = Intent(this@MainActivity, EventDetailsActivity::class.java).apply {
                    putExtra(EventDetailsActivity.EXTRA_EVENT_ID, event.id)
                    putExtra(EventDetailsActivity.EXTRA_EVENT_TITLE, event.title)
                    putExtra(EventDetailsActivity.EXTRA_EVENT_START, event.startTime)
                    putExtra(EventDetailsActivity.EXTRA_EVENT_END, event.endTime)
                    putExtra(EventDetailsActivity.EXTRA_EVENT_LOCATION, event.location)
                    putExtra(EventDetailsActivity.EXTRA_EVENT_DESCRIPTION, event.description)
                    putExtra(EventDetailsActivity.EXTRA_EVENT_ALL_DAY, event.isAllDay)
                    putExtra(EventDetailsActivity.EXTRA_EVENT_COLOR, event.color)
                    putExtra(EventDetailsActivity.EXTRA_EVENT_SOURCE, event.calendarSource)
                }
                startActivity(intent)
            }
        }
        
        binding.eventsRecyclerView.adapter = adapter
        eventAdapter = adapter
    }

    private fun setupPullToRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            lifecycleScope.launch {
                syncManager.performFullSync()
                binding.swipeRefresh.isRefreshing = false
            }
        }
    }

    private fun setupFab() {
        binding.fabAdd.setOnClickListener {
            openGoogleCalendar()
        }
    }

    private fun setupEmptyState() {
        binding.btnSyncNow.setOnClickListener {
            lifecycleScope.launch {
                binding.swipeRefresh.isRefreshing = true
                syncManager.performFullSync()
                binding.swipeRefresh.isRefreshing = false
            }
        }
    }

    private fun observeEvents() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Get all events (from epoch 0 = all events)
                eventRepository.getEventsFromDate(0)
                    .collectLatest { events ->
                        eventAdapter.submitEvents(events)
                        
                        // Show/hide empty state
                        if (events.isEmpty()) {
                            binding.emptyStateContainer.visibility = android.view.View.VISIBLE
                            binding.eventsRecyclerView.visibility = android.view.View.GONE
                        } else {
                            binding.emptyStateContainer.visibility = android.view.View.GONE
                            binding.eventsRecyclerView.visibility = android.view.View.VISIBLE
                        }
                    }
            }
        }
        
        // Check for smart sync when app opens
        if (syncManager.shouldPerformSmartSync()) {
            lifecycleScope.launch {
                binding.swipeRefresh.isRefreshing = true
                syncManager.performFullSync()
                binding.swipeRefresh.isRefreshing = false
            }
        }
    }

    private fun openGoogleCalendar() {
        try {
            // Try to open Google Calendar app first
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = android.net.Uri.parse("https://calendar.google.com")
            }
            
            // Check if there's an app that can handle this intent
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                // No app available, try browser
                val browserIntent = Intent(Intent.ACTION_VIEW, android.net.Uri.parse("https://calendar.google.com"))
                if (browserIntent.resolveActivity(packageManager) != null) {
                    startActivity(browserIntent)
                } else {
                    Toast.makeText(this, "No browser or calendar app available", Toast.LENGTH_LONG).show()
                }
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Cannot open Google Calendar: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}
