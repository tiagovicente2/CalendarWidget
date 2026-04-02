package com.calendar.widget.ui.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.calendar.widget.util.Logger
import com.calendar.widget.R
import com.calendar.widget.data.local.prefs.PreferenceKeys
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

    private val authLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            performSync()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.d("MainActivity", "onCreate called")
        
        handleIntent(intent)
        
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        Logger.d("MainActivity", "Setting up views")
        setupRecyclerView()
        setupPullToRefresh()
        setupEmptyState()
        
        Logger.d("MainActivity", "Observing events")
        observeEvents()
        
        Logger.d("MainActivity", "Checking sign in")
        checkGoogleSignIn()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        val eventId = intent.getStringExtra(EventDetailsActivity.EXTRA_EVENT_ID)
        Logger.d("MainActivity", "handleIntent: eventId=$eventId")
        if (eventId != null) {
            val detailIntent = Intent(this, EventDetailsActivity::class.java).apply {
                // Forward all extras
                putExtras(intent)
                // Ensure we don't stack multiple detail views
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            }
            startActivity(detailIntent)
        }
    }

    private fun checkGoogleSignIn() {
        val account = getSavedGoogleAccount()
        if (account == null) {
            launchGoogleSignIn()
        }
    }

    private fun launchGoogleSignIn() {
        lifecycleScope.launch {
            val credentialManager = CredentialManager.create(this@MainActivity)
            val webClientId = getString(R.string.default_web_client_id)
            
            val googleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(webClientId)
                .setAutoSelectEnabled(false)
                .setNonce(java.util.UUID.randomUUID().toString())
                .build()

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            try {
                val result = credentialManager.getCredential(
                    request = request,
                    context = this@MainActivity
                )
                
                val credential = result.credential
                if (credential is androidx.credentials.CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                    // We extract email from the token subject/id
                    val email = googleIdTokenCredential.id
                    saveGoogleAccount(email)
                    Toast.makeText(this@MainActivity, "Connected: $email", Toast.LENGTH_SHORT).show()
                    performSync()
                }
            } catch (e: GetCredentialException) {
                Logger.e("MainActivity", "GetCredentialException: ${e.type} - ${e.errorMessage}", e)
                Toast.makeText(this@MainActivity, "Sign in failed: ${e.type}", Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                Logger.e("MainActivity", "Exception", e)
                Toast.makeText(this@MainActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveGoogleAccount(accountName: String?) {
        val prefs = getSharedPreferences(PreferenceKeys.PREFS_NAME, Context.MODE_PRIVATE)
        if (accountName != null) {
            prefs.edit().putString(PreferenceKeys.GOOGLE_ACCOUNT, accountName).apply()
        } else {
            prefs.edit().remove(PreferenceKeys.GOOGLE_ACCOUNT).apply()
        }
    }

    private fun getSavedGoogleAccount(): String? {
        val prefs = getSharedPreferences(PreferenceKeys.PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getString(PreferenceKeys.GOOGLE_ACCOUNT, null)
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
            performSync()
        }
    }

    private fun performSync() {
        Logger.d("MainActivity", "performSync triggered")
        lifecycleScope.launch {
            binding.swipeRefresh.isRefreshing = true
            try {
                syncManager.performFullSync()
                Logger.d("MainActivity", "performSync completed successfully")
            } catch (e: com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException) {
                Logger.w("MainActivity", "Authorization needed, launching intent")
                authLauncher.launch(e.intent)
            } catch (e: Exception) {
                Logger.e("MainActivity", "Sync failed", e)
                Toast.makeText(this@MainActivity, "Sync failed: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                binding.swipeRefresh.isRefreshing = false
            }
        }
    }

    private fun setupEmptyState() {
        binding.btnSyncNow.setOnClickListener {
            if (getSavedGoogleAccount() == null) {
                launchGoogleSignIn()
            } else {
                performSync()
            }
        }
    }

    private fun observeEvents() {
        val calendar = java.util.Calendar.getInstance()
        calendar.set(java.util.Calendar.HOUR_OF_DAY, 0)
        calendar.set(java.util.Calendar.MINUTE, 0)
        calendar.set(java.util.Calendar.SECOND, 0)
        calendar.set(java.util.Calendar.MILLISECOND, 0)
        val startOfToday = calendar.timeInMillis

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Get all events from today onwards
                eventRepository.getEventsFromDate(startOfToday)
                    .collectLatest { events ->
                        eventAdapter.submitEvents(events)
                        updateUiState(events)
                    }
            }
        }
        
        // Check for smart sync when app opens
        if (syncManager.shouldPerformSmartSync()) {
            performSync()
        }
    }

    private fun updateUiState(events: List<com.calendar.widget.data.model.Event>) {
        if (events.isEmpty()) {
            if (!binding.swipeRefresh.isRefreshing) {
                binding.emptyStateContainer.visibility = android.view.View.VISIBLE
                binding.eventsRecyclerView.visibility = android.view.View.GONE
            }
        } else {
            binding.emptyStateContainer.visibility = android.view.View.GONE
            binding.eventsRecyclerView.visibility = android.view.View.VISIBLE
        }
    }

}