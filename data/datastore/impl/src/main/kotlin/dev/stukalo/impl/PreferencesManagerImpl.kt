package dev.stukalo.impl

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.stukalo.datastore.Constants
import dev.stukalo.datastore.PreferencesManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences>
    by preferencesDataStore(name = "UserPreferences")

class PreferencesManagerImpl
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
    ) : PreferencesManager {
        companion object {
            private val ONBOARDING_KEY = booleanPreferencesKey("onboarding_showed")
            private val VELOCITY_KEY = stringPreferencesKey("velocity_unit")
            private val DISTANCE_KEY = stringPreferencesKey("distance_unit")
            private val DIAMETER_KEY = stringPreferencesKey("diameter_unit")
            private val SYNCHRONIZATION_KEY = stringPreferencesKey("synchronization_time")
            private val INTERVAL_KEY = stringPreferencesKey("minimum_interval")
        }

        override fun onboardingShowed(): Flow<Boolean> {
            return context.dataStore.data.map { preferences ->
                preferences[ONBOARDING_KEY] ?: false
            }
        }

        override suspend fun setOnboardingShowed(isShowed: Boolean) {
            context.dataStore.edit { preferences ->
                preferences[ONBOARDING_KEY] = isShowed
            }
        }

        override fun selectedVelocityUnit(): Flow<String> {
            return context.dataStore.data.map { preferences ->
                preferences[VELOCITY_KEY] ?: Constants.Velocity.KILOMETERS_PER_SECOND.value
            }
        }

        override suspend fun setVelocityUnit(velocityUnit: String) {
            context.dataStore.edit { preferences ->
                preferences[VELOCITY_KEY] = velocityUnit
            }
        }

        override fun selectedDistanceUnit(): Flow<String> {
            return context.dataStore.data.map { preferences ->
                preferences[DISTANCE_KEY] ?: Constants.Distance.KILOMETERS.value
            }
        }

        override suspend fun setDistanceUnit(distanceUnit: String) {
            context.dataStore.edit { preferences ->
                preferences[DISTANCE_KEY] = distanceUnit
            }
        }

        override fun selectedDiameterUnit(): Flow<String> {
            return context.dataStore.data.map { preferences ->
                preferences[DIAMETER_KEY] ?: Constants.Diameter.KILOMETERS.value
            }
        }

        override suspend fun setDiameterUnit(diameterUnit: String) {
            context.dataStore.edit { preferences ->
                preferences[DIAMETER_KEY] = diameterUnit
            }
        }

        override fun selectedSynchronizationTime(): Flow<String> {
            return context.dataStore.data.map { preferences ->
                preferences[SYNCHRONIZATION_KEY] ?: Constants.SynchronizationTime.H1.value
            }
        }

        override suspend fun setSynchronizationTime(time: String) {
            context.dataStore.edit { preferences ->
                preferences[SYNCHRONIZATION_KEY] = time
            }
        }

        override fun selectedMinimumInterval(): Flow<String> {
            return context.dataStore.data.map { preferences ->
                preferences[INTERVAL_KEY] ?: Constants.MinimumInterval.H24.value
            }
        }

        override suspend fun setMinimumInterval(interval: String) {
            context.dataStore.edit { preferences ->
                preferences[INTERVAL_KEY] = interval
            }
        }
    }
