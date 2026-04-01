package com.calendar.widget.ui.settings

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.calendar.widget.databinding.ActivitySettingsBinding
import com.calendar.widget.sync.SyncManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import com.google.api.services.calendar.CalendarScopes
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Settings activity for managing calendar sources.
 * Includes Google Sign-In and iCal URL management.
 */
@AndroidEntryPoint
class SettingsActivity : AppCompatActivity() {

    @Inject
    lateinit var syncManager: SyncManager

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var icalUrlAdapter: IcalUrlAdapter

    private val signInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            handleSignInResult(result.data)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupGoogleSignIn()
        setupIcalUrlList()
        setupAddIcalUrl()
        refreshUi()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Settings"
    }

    private fun setupGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestScopes(Scope(CalendarScopes.CALENDAR_READONLY))
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // Check if already signed in
        val account = GoogleSignIn.getLastSignedInAccount(this)
        updateGoogleSignInUi(account)

        binding.btnGoogleSignIn.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            signInLauncher.launch(signInIntent)
        }

        binding.btnGoogleSignOut.setOnClickListener {
            googleSignInClient.signOut().addOnCompleteListener {
                updateGoogleSignInUi(null)
                Toast.makeText(this, "Signed out", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleSignInResult(data: Intent?) {
        try {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)
            updateGoogleSignInUi(account)
            
            // Trigger sync with new account
            lifecycleScope.launch {
                syncManager.performFullSync()
                Toast.makeText(this@SettingsActivity, "Signed in as ${account?.email}", Toast.LENGTH_SHORT).show()
            }
        } catch (e: ApiException) {
            Toast.makeText(this, "Sign in failed: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun updateGoogleSignInUi(account: GoogleSignInAccount?) {
        if (account != null) {
            binding.tvGoogleAccount.text = "Connected: ${account.email}"
            binding.btnGoogleSignIn.visibility = android.view.View.GONE
            binding.btnGoogleSignOut.visibility = android.view.View.VISIBLE
        } else {
            binding.tvGoogleAccount.text = "Not connected"
            binding.btnGoogleSignIn.visibility = android.view.View.VISIBLE
            binding.btnGoogleSignOut.visibility = android.view.View.GONE
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
        lifecycleScope.launch {
            syncManager.performFullSync()
            Toast.makeText(this@SettingsActivity, "Added calendar", Toast.LENGTH_SHORT).show()
        }
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
