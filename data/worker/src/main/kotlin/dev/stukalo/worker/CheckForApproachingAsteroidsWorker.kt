package dev.stukalo.worker

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dev.stukalo.common.utils.AsteroidAdapter
import dev.stukalo.database.repo.FavoriteAsteroidsRepository
import dev.stukalo.mapper.mapToAsteroidUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.concurrent.TimeUnit
import kotlin.time.Duration.Companion.hours
import kotlin.time.DurationUnit

const val ASTEROID_DETAILS_DEEPLINK = "asteroids://details?asteroid_ui_json="

@HiltWorker
class CheckForApproachingAsteroidsWorker
    @AssistedInject
    constructor(
        @Assisted appContext: Context,
        @Assisted params: WorkerParameters,
        private val repository: FavoriteAsteroidsRepository,
    ) : CoroutineWorker(appContext, params) {
        private val notificationManager: NotificationManager =
            applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        override suspend fun doWork(): Result =
            withContext(Dispatchers.IO) {
                try {
                    val favoriteAsteroids = repository.getFavoriteAsteroids().first()

                    favoriteAsteroids?.forEach {
                        val notificationId = it.id?.toInt() ?: 1
                        val dateTime =
                            LocalDateTime.ofInstant(
                                Instant.ofEpochMilli(it.closeApproachData?.epochDateCloseApproach ?: 0),
                                ZoneId.systemDefault(),
                            )

                        val futureTimeSeconds = dateTime.atZone(ZoneId.systemDefault()).toEpochSecond()
                        val currentTimeSeconds = LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond()

                        val timeDifferenceSeconds = futureTimeSeconds - currentTimeSeconds
                        val twentyFourHoursInSeconds = 24.hours.toLong(DurationUnit.SECONDS)

                        val isWithin24Hours = timeDifferenceSeconds in 1..<twentyFourHoursInSeconds

                        if (isWithin24Hours && !it.isShownToUser) {
                            val channelId = "asteroid_notification"
                            val name = "asteroid_channel"
                            val importance = NotificationManager.IMPORTANCE_HIGH
                            val channel = NotificationChannel(channelId, name, importance)
                            notificationManager.createNotificationChannel(channel)

                            val title = applicationContext.getString(R.string.attention)
                            val text =
                                String.format(
                                    applicationContext.getString(R.string.asteroid_is_approaching),
                                    it.name,
                                )
                            val icon = R.drawable.ic_asteroid

                            val asteroidUiJson = AsteroidAdapter.toJson(it.mapToAsteroidUi())
                            val deeplink = ASTEROID_DETAILS_DEEPLINK + asteroidUiJson

                            val intent = Intent()
                            intent.action = Intent.ACTION_VIEW
                            intent.data = Uri.parse(deeplink)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            val pendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, PendingIntent.FLAG_IMMUTABLE)

                            val notificationBuilder =
                                NotificationCompat.Builder(applicationContext, channelId)
                                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                    .setAutoCancel(true)
                                    .setSmallIcon(icon)
                                    .setContentTitle(title)
                                    .setContentText(text)
                                    .setContentIntent(pendingIntent)

                            with(NotificationManagerCompat.from(applicationContext)) {
                                if (ActivityCompat.checkSelfPermission(
                                        applicationContext,
                                        Manifest.permission.POST_NOTIFICATIONS,
                                    ) == PackageManager.PERMISSION_GRANTED
                                ) {
                                    notify(notificationId, notificationBuilder.build())
                                }
                            }
                        }
                    }

                    Result.success()
                } catch (e: Exception) {
                    Result.retry()
                }
            }

        companion object {
            fun createPeriodicRequest(): PeriodicWorkRequest {
                return PeriodicWorkRequestBuilder<CheckForApproachingAsteroidsWorker>(1, TimeUnit.HOURS)
                    .addTag("CheckForApproachingAsteroidsWork")
                    .build()
            }
        }
    }
