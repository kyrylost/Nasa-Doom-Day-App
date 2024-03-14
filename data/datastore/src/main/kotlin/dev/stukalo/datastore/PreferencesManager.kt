package dev.stukalo.datastore

import kotlinx.coroutines.flow.Flow

interface PreferencesManager {
    fun onboardingShowed(): Flow<Boolean>

    suspend fun setOnboardingShowed(isShowed: Boolean)

    fun selectedVelocityUnit(): Flow<String>

    suspend fun setVelocityUnit(velocityUnit: String)

    fun selectedDistanceUnit(): Flow<String>

    suspend fun setDistanceUnit(distanceUnit: String)

    fun selectedDiameterUnit(): Flow<String>

    suspend fun setDiameterUnit(diameterUnit: String)

    fun selectedSynchronizationTime(): Flow<String>

    suspend fun setSynchronizationTime(time: String)

    fun selectedMinimumInterval(): Flow<String>

    suspend fun setMinimumInterval(interval: String)
}
