package dev.stukalo.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.stukalo.datastore.PreferencesManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel
    @Inject
    constructor(
        private val datastore: PreferencesManager,
    ) : ViewModel() {
        private val _settingsStateFlow = MutableStateFlow(SettingsUiState())
        val settingsStateFlow = _settingsStateFlow.asStateFlow()

        data class SettingsUiState(
            val velocityUnit: String? = null,
            val distanceUnit: String? = null,
            val diameterUnit: String? = null,
            val synchronizationTime: String? = null,
            val minimumInterval: String? = null,
        )

        fun getSelectedValues() =
            viewModelScope.launch {
                _settingsStateFlow.update {
                    it.copy(
                        velocityUnit = getSelectedVelocityUnit(),
                        distanceUnit = getSelectedDistanceUnit(),
                        diameterUnit = getSelectedDiameterUnit(),
                        synchronizationTime = getSelectedSynchronizationTime(),
                        minimumInterval = getSelectedMinimumInterval(),
                    )
                }
            }

        fun setNewValues(
            velocityUnit: String,
            distanceUnit: String,
            diameterUnit: String,
            synchronizationTime: String,
            minimumInterval: String,
        ) = viewModelScope.launch {
            if (velocityUnit != settingsStateFlow.value.velocityUnit) {
                setVelocityUnit(velocityUnit)
            }
            if (distanceUnit != settingsStateFlow.value.distanceUnit) {
                setDistanceUnit(distanceUnit)
            }
            if (diameterUnit != settingsStateFlow.value.diameterUnit) {
                setDiameterUnit(diameterUnit)
            }
            if (synchronizationTime != settingsStateFlow.value.synchronizationTime) {
                setSynchronizationTime(synchronizationTime)
            }
            if (minimumInterval != settingsStateFlow.value.minimumInterval) {
                setMinimumInterval(minimumInterval)
            }
        }

        private suspend fun getSelectedVelocityUnit(): String {
            return withContext(Dispatchers.IO) {
                datastore.selectedVelocityUnit().first()
            }
        }

        private suspend fun setVelocityUnit(velocityUnit: String) = datastore.setVelocityUnit(velocityUnit)

        private suspend fun getSelectedDistanceUnit(): String {
            return withContext(Dispatchers.IO) {
                datastore.selectedDistanceUnit().first()
            }
        }

        private suspend fun setDistanceUnit(distanceUnit: String) = datastore.setDistanceUnit(distanceUnit)

        private suspend fun getSelectedDiameterUnit(): String {
            return withContext(Dispatchers.IO) {
                datastore.selectedDiameterUnit().first()
            }
        }

        private suspend fun setDiameterUnit(diameterUnit: String) = datastore.setDiameterUnit(diameterUnit)

        private suspend fun getSelectedSynchronizationTime(): String {
            return withContext(Dispatchers.IO) {
                datastore.selectedSynchronizationTime().first()
            }
        }

        private suspend fun setSynchronizationTime(time: String) = datastore.setSynchronizationTime(time)

        private suspend fun getSelectedMinimumInterval(): String {
            return withContext(Dispatchers.IO) {
                datastore.selectedMinimumInterval().first()
            }
        }

        private suspend fun setMinimumInterval(interval: String) = datastore.setMinimumInterval(interval)
    }
