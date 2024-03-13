package dev.stukalo.asteroiddetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.stukalo.asteroiddetails.util.mapToAsteroidRepo
import dev.stukalo.common.model.AsteroidUi
import dev.stukalo.database.repo.FavoriteAsteroidsRepository
import dev.stukalo.datastore.PreferencesManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AsteroidDetailsViewModel
    @Inject
    constructor(
        private val favoriteAsteroidsRepository: FavoriteAsteroidsRepository,
        private val datastore: PreferencesManager,
    ) : ViewModel() {
        private val _addStatusSharedFlow = MutableSharedFlow<String?>()
        val addStatusSharedFlow = _addStatusSharedFlow.asSharedFlow()

        fun addToFavorite(asteroidUi: AsteroidUi) {
            viewModelScope.launch(Dispatchers.IO) {
                val asteroidRepo = asteroidUi.mapToAsteroidRepo()
                if (asteroidRepo?.id != null) {
                    favoriteAsteroidsRepository.insertAsteroid(asteroidRepo).also {
                        if (it != -1L) {
                            _addStatusSharedFlow.emit(asteroidUi.name)
                        } else {
                            _addStatusSharedFlow.emit(null)
                        }
                    }
                } else {
                    _addStatusSharedFlow.emit(null)
                }
            }
        }

        suspend fun isAsteroidInFavorite(id: String?): Boolean =
            withContext(Dispatchers.IO) {
                val asteroid = favoriteAsteroidsRepository.getAsteroidById(id)
                asteroid != null
            }

        fun updateIsShownField(id: String?) {
            viewModelScope.launch(Dispatchers.IO) {
                id?.let {
                    favoriteAsteroidsRepository.updateIsShownField(id, true)
                }
            }
        }

        suspend fun allowComparing(): Boolean {
            return withContext(Dispatchers.IO) {
                (favoriteAsteroidsRepository.getFavoriteAsteroids().first()?.count() ?: 0) >= 2
            }
        }

        suspend fun getSelectedVelocityUnit(): String {
            return withContext(Dispatchers.IO) {
                datastore.selectedVelocityUnit().first()
            }
        }

        suspend fun getSelectedDistanceUnit(): String {
            return withContext(Dispatchers.IO) {
                datastore.selectedDistanceUnit().first()
            }
        }

        suspend fun getSelectedDiameterUnit(): String {
            return withContext(Dispatchers.IO) {
                datastore.selectedDiameterUnit().first()
            }
        }
    }
