package com.calendar.widget.util

import android.util.Log
import com.calendar.widget.BuildConfig

/**
 * Centralized logging utility that only logs in debug builds.
 */
object Logger {
    private const val TAG = "CalendarWidget"
    
    fun d(subTag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.d("$TAG.$subTag", message)
        }
    }
    
    fun e(subTag: String, message: String, throwable: Throwable? = null) {
        if (BuildConfig.DEBUG) {
            if (throwable != null) {
                Log.e("$TAG.$subTag", message, throwable)
            } else {
                Log.e("$TAG.$subTag", message)
            }
        }
    }
    
    fun w(subTag: String, message: String, throwable: Throwable? = null) {
        if (BuildConfig.DEBUG) {
            if (throwable != null) {
                Log.w("$TAG.$subTag", message, throwable)
            } else {
                Log.w("$TAG.$subTag", message)
            }
        }
    }
}