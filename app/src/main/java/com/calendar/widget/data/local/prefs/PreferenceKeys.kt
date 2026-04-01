package com.calendar.widget.data.local.prefs

/**
 * Centralized preference keys to prevent typos and enable easier refactoring.
 */
object PreferenceKeys {
    const val PREFS_NAME = "calendar_widget_prefs"
    
    // Google Account
    const val GOOGLE_ACCOUNT = "google_account"
    
    // Selected Calendars
    const val SELECTED_GOOGLE_CALENDARS = "selected_google_calendars"
    
    // iCal URLs
    const val ICAL_URLS = "ical_urls"
    
    // Sync Settings
    const val LAST_SYNC_TIMESTAMP = "last_sync_timestamp"
    const val SYNC_INTERVAL_HOURS = "sync_interval_hours"
}