package dev.stukalo.asteroiddetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.stukalo.asteroiddetails.util.mapToAsteroidRepo
import dev.stukalo.common.model.AsteroidUi
import dev.stukalo.database.repo.FavoriteAsteroidsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume

@HiltViewModel
class AsteroidDetailsViewModel
    @Inject
    constructor(
        private val favoriteAsteroidsRepository: FavoriteAsteroidsRepository,
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

        suspend fun isAsteroidInFavorite(id: String?): Boolean {
            return withContext(Dispatchers.IO) {
                suspendCancellableCoroutine { continuation ->
                    val asteroid = favoriteAsteroidsRepository.getAsteroidById(id)
                    if (asteroid != null) {
                        continuation.resume(true)
                    } else {
                        continuation.resume(false)
                    }
                }
            }
        }

        fun updateIsShownField(id: String?) {
            viewModelScope.launch(Dispatchers.IO) {
                id?.let {
                    favoriteAsteroidsRepository.updateIsShownField(id, true)
                }
            }
        }
    }
