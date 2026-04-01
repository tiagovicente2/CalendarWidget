package com.calendar.widget.ui.config

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.calendar.widget.R
import com.calendar.widget.databinding.ActivityConfigBinding
import com.calendar.widget.service.FloatingCalendarService
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main configuration activity for the Calendar Widget app.
 * Provides onboarding and settings for the floating calendar widget.
 */
@AndroidEntryPoint
class ConfigActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConfigBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfigBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        // Show WelcomeFragment
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, WelcomeFragment())
                .commitNow()
        }

        // If this is first launch, start service
        if (!FloatingCalendarService.isRunning) {
            startFloatingService()
        }
    }

    private fun startFloatingService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            requestOverlayPermission()
        } else {
            val intent = Intent(this, FloatingCalendarService::class.java).apply {
                action = FloatingCalendarService.ACTION_SHOW
            }
            startService(intent)
        }
    }

    private fun requestOverlayPermission() {
        val intent = Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            Uri.parse("package:$packageName")
        )
        startActivityForResult(intent, REQUEST_OVERLAY_PERMISSION)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_OVERLAY_PERMISSION) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Settings.canDrawOverlays(this)) {
                startFloatingService()
            } else {
                Toast.makeText(this, "Overlay permission is required", Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object {
        private const val REQUEST_OVERLAY_PERMISSION = 1001
    }
}
