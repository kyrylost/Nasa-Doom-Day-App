package dev.stukalo.favoriteasteroids

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.stukalo.common.model.AsteroidUi
import dev.stukalo.domain.DeleteFromFavoriteUseCase
import dev.stukalo.domain.GetFavoriteAsteroidsUseCase
import dev.stukalo.favoriteasteroids.util.mapper.mapToAsteroidUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteAsteroidsViewModel
    @Inject
    constructor(
        private val getFavoriteAsteroidsUseCase: GetFavoriteAsteroidsUseCase,
        private val deleteFromFavoriteAsteroidsUseCase: DeleteFromFavoriteUseCase,
    ) : ViewModel() {
        private val _favoriteAsteroidsStateFlow = MutableStateFlow(FavoriteAsteroidsUiState())
        val favoriteAsteroidsStateFlow = _favoriteAsteroidsStateFlow.asStateFlow()

        data class FavoriteAsteroidsUiState(
            val favoriteAsteroids: List<AsteroidUi> = emptyList(),
        )

        fun collectAsteroids() =
            viewModelScope.launch {
                getFavoriteAsteroidsUseCase().collect { listOfAsteroids ->
                    if (listOfAsteroids != null) {
                        _favoriteAsteroidsStateFlow.update { state ->
                            state.copy(
                                favoriteAsteroids =
                                    listOfAsteroids.map {
                                        it.mapToAsteroidUi()
                                    },
                            )
                        }
                    } else {
                        _favoriteAsteroidsStateFlow.update {
                            it.copy(favoriteAsteroids = emptyList())
                        }
                    }
                }
            }

        fun deleteAsteroid(id: String?) = deleteFromFavoriteAsteroidsUseCase(id)
    }
