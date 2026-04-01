package com.calendar.widget.ui.config

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.calendar.widget.databinding.FragmentWelcomeBinding
import com.calendar.widget.service.FloatingCalendarService
import dagger.hilt.android.AndroidEntryPoint

/**
 * Welcome/onboarding fragment for first-time users.
 * Guides users through enabling the floating calendar widget.
 */
@AndroidEntryPoint
class WelcomeFragment : Fragment() {

    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnStart.setOnClickListener {
            // Navigate to Google Sign-in
            // TODO: Add navigation to GoogleSignInFragment
            Toast.makeText(context, "Configuration flow - Phase 2", Toast.LENGTH_SHORT).show()
        }

        binding.btnEnableWidget.setOnClickListener {
            enableFloatingWidget()
        }
    }

    private fun enableFloatingWidget() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && 
            !Settings.canDrawOverlays(requireContext())) {
            requestOverlayPermission()
        } else {
            startFloatingService()
        }
    }

    private fun requestOverlayPermission() {
        val intent = Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            Uri.parse("package:${requireContext().packageName}")
        )
        startActivity(intent)
    }

    private fun startFloatingService() {
        val intent = Intent(requireContext(), FloatingCalendarService::class.java).apply {
            action = FloatingCalendarService.ACTION_SHOW
        }
        requireContext().startService(intent)
        Toast.makeText(context, "Widget enabled!", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
