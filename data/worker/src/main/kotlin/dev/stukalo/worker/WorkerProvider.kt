package dev.stukalo.worker

import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import dev.stukalo.datastore.Constants
import dev.stukalo.datastore.PreferencesManager
import kotlinx.coroutines.flow.first
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class WorkerProvider
    @Inject
    constructor(
        private val datastore: PreferencesManager,
    ) {
        suspend fun createPeriodicRequest(): PeriodicWorkRequest {
            val synchronizationTime: Long =
                when (datastore.selectedSynchronizationTime().first()) {
                    Constants.SynchronizationTime.M30.value -> 30
                    Constants.SynchronizationTime.H1.value -> 60
                    Constants.SynchronizationTime.H2.value -> 120
                    Constants.SynchronizationTime.H6.value -> 360
                    else -> {
                        60
                    }
                }
            return PeriodicWorkRequestBuilder<CheckForApproachingAsteroidsWorker>(synchronizationTime, TimeUnit.MINUTES)
                .addTag("CheckForApproachingAsteroidsWork")
                .build()
        }
    }
