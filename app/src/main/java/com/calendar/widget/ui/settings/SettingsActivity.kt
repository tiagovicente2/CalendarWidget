package com.calendar.widget.ui.settings

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.calendar.widget.R
import com.calendar.widget.data.local.prefs.PreferenceKeys
import com.calendar.widget.databinding.ActivitySettingsBinding
import com.calendar.widget.sync.SyncManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SettingsActivity : AppCompatActivity() {

    @Inject
    lateinit var syncManager: SyncManager

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var icalUrlAdapter: IcalUrlAdapter
    private lateinit var googleCalendarAdapter: GoogleCalendarAdapter

    private val authLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            triggerSync()
            fetchGoogleCalendars()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupGoogleSignIn()
        setupGoogleCalendarList()
        setupIcalUrlList()
        setupAddIcalUrl()
        refreshUi()
        fetchGoogleCalendars()
    }

    private fun setupGoogleCalendarList() {
        googleCalendarAdapter = GoogleCalendarAdapter { calendarId, isSelected ->
            val currentSelected = syncManager.getSelectedGoogleCalendars().toMutableSet()
            if (isSelected) {
                currentSelected.add(calendarId)
            } else {
                currentSelected.remove(calendarId)
            }
            syncManager.setSelectedGoogleCalendars(currentSelected.toList())
            triggerSync()
        }
        
        binding.recyclerGoogleCalendars.layoutManager = LinearLayoutManager(this)
        binding.recyclerGoogleCalendars.adapter = googleCalendarAdapter
    }

    private fun fetchGoogleCalendars() {
        if (getSavedGoogleAccount() != null) {
            lifecycleScope.launch {
                try {
                    val calendars = syncManager.getGoogleCalendarList()
                    if (calendars.isNotEmpty()) {
                        googleCalendarAdapter.submitList(calendars)
                        googleCalendarAdapter.setSelectedIds(syncManager.getSelectedGoogleCalendars())
                        binding.tvGoogleCalendarsTitle.visibility = android.view.View.VISIBLE
                        binding.recyclerGoogleCalendars.visibility = android.view.View.VISIBLE
                    }
                } catch (e: com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException) {
                    // Handled in triggerSync or manual click
                } catch (e: Exception) {
                    android.util.Log.e("SettingsActivity", "Error fetching calendars", e)
                }
            }
        } else {
            binding.tvGoogleCalendarsTitle.visibility = android.view.View.GONE
            binding.recyclerGoogleCalendars.visibility = android.view.View.GONE
        }
    }

    private fun triggerSync() {
        lifecycleScope.launch {
            try {
                syncManager.performFullSync()
            } catch (e: com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException) {
                authLauncher.launch(e.intent)
            } catch (e: Exception) {
                Toast.makeText(this@SettingsActivity, "Sync failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Settings"
    }

    private fun setupGoogleSignIn() {
        val currentAccount = getSavedGoogleAccount()
        updateGoogleSignInUi(currentAccount)

        binding.btnGoogleSignIn.setOnClickListener {
            launchGoogleSignIn()
        }

        binding.btnGoogleSignOut.setOnClickListener {
            saveGoogleAccount(null)
            updateGoogleSignInUi(null)
            Toast.makeText(this, "Disconnected", Toast.LENGTH_SHORT).show()
        }
    }

    private fun launchGoogleSignIn() {
        lifecycleScope.launch {
            val credentialManager = CredentialManager.create(this@SettingsActivity)
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
                    context = this@SettingsActivity
                )
                
                val credential = result.credential
                if (credential is androidx.credentials.CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                    val email = googleIdTokenCredential.id
                    saveGoogleAccount(email)
                    updateGoogleSignInUi(email)
                    
                    // Trigger sync with new account
                    triggerSync()
                    Toast.makeText(this@SettingsActivity, "Connected to $email", Toast.LENGTH_SHORT).show()
                }
            } catch (e: GetCredentialException) {
                android.util.Log.e("SettingsActivity", "GetCredentialException: ${e.type} - ${e.errorMessage}", e)
                Toast.makeText(this@SettingsActivity, "Sign in failed: ${e.type}", Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                android.util.Log.e("SettingsActivity", "Exception", e)
                Toast.makeText(this@SettingsActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
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

    private fun updateGoogleSignInUi(accountName: String?) {
        if (accountName != null) {
            binding.tvGoogleAccount.text = "Connected: $accountName"
            binding.btnGoogleSignIn.visibility = android.view.View.GONE
            binding.btnGoogleSignOut.visibility = android.view.View.VISIBLE
            fetchGoogleCalendars()
        } else {
            binding.tvGoogleAccount.text = "Not connected"
            binding.btnGoogleSignIn.visibility = android.view.View.VISIBLE
            binding.btnGoogleSignOut.visibility = android.view.View.GONE
            binding.tvGoogleCalendarsTitle.visibility = android.view.View.GONE
            binding.recyclerGoogleCalendars.visibility = android.view.View.GONE
        }
    }

    private fun setupIcalUrlList() {
        icalUrlAdapter = IcalUrlAdapter { url ->
            removeIcalUrl(url)
        }
        
        binding.recyclerIcalUrls.layoutManager = LinearLayoutManager(this)
        binding.recyclerIcalUrls.adapter = icalUrlAdapter
    }

    private fun setupAddIcalUrl() {
        binding.btnAddIcalUrl.setOnClickListener {
            val url = binding.etIcalUrl.text?.toString()?.trim() ?: ""
            if (url.isNotEmpty() && (url.startsWith("http://") || url.startsWith("https://"))) {
                addIcalUrl(url)
                binding.etIcalUrl.text?.clear()
            } else {
                Toast.makeText(this, "Please enter a valid URL (http:// or https://)", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addIcalUrl(url: String) {
        syncManager.addIcalUrl(url)
        refreshUi()
        
        // Trigger sync
        triggerSync()
        Toast.makeText(this@SettingsActivity, "Added calendar", Toast.LENGTH_SHORT).show()
    }

    private fun removeIcalUrl(url: String) {
        syncManager.removeIcalUrl(url)
        refreshUi()
        Toast.makeText(this, "Removed calendar", Toast.LENGTH_SHORT).show()
    }

    private fun refreshUi() {
        val urls = syncManager.getIcalUrls()
        icalUrlAdapter.submitList(urls)
        
        if (urls.isEmpty()) {
            binding.tvNoIcalUrls.visibility = android.view.View.VISIBLE
            binding.recyclerIcalUrls.visibility = android.view.View.GONE
        } else {
            binding.tvNoIcalUrls.visibility = android.view.View.GONE
            binding.recyclerIcalUrls.visibility = android.view.View.VISIBLE
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}