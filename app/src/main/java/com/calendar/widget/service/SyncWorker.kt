package com.calendar.widget.service

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.calendar.widget.sync.SyncManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

/**
 * WorkManager worker for performing background calendar sync.
 * Runs periodically to keep calendar data up to date.
 */
@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val syncManager: SyncManager
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        if (!isNetworkAvailable()) {
            return Result.retry()
        }

        return try {
            syncManager.performFullSync()
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    companion object {
        const val WORK_NAME = "calendar_sync_work"
    }
}
