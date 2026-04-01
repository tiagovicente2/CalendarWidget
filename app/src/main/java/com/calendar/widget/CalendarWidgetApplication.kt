package com.calendar.widget

import android.app.Application
import com.google.android.material.color.DynamicColors
import dagger.hilt.android.HiltAndroidApp

/**
 * Application class for Calendar Widget.
 * Entry point for Hilt dependency injection.
 */
@HiltAndroidApp
class CalendarWidgetApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}
