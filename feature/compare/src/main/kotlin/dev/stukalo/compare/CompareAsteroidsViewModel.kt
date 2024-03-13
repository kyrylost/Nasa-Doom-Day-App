package dev.stukalo.compare

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.stukalo.common.model.AsteroidUi
import dev.stukalo.compare.mapper.mapToAsteroidUi
import dev.stukalo.database.repo.FavoriteAsteroidsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompareAsteroidsViewModel
    @Inject
    constructor(
        private val favoriteAsteroidsRepository: FavoriteAsteroidsRepository,
    ) : ViewModel() {
        private val _compareStateFlow = MutableStateFlow<List<AsteroidUi>>(emptyList())
        val compareStateFlow = _compareStateFlow.asStateFlow()

        fun getFavoriteAsteroids() {
            viewModelScope.launch {
                _compareStateFlow.emit(
                    favoriteAsteroidsRepository.getFavoriteAsteroids().first()?.map {
                        it.mapToAsteroidUi()
                    } ?: emptyList(),
                )
            }
        }

        fun clear() {
            viewModelScope.launch {
                _compareStateFlow.emit(emptyList())
            }
        }
    }
