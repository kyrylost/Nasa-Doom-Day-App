package dev.stukalo.asteroids

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.stukalo.repository.model.AsteroidRepo
import dev.stukalo.repository.repo.AsteroidsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AsteroidsViewModel @Inject constructor(
    private val asteroidsRepository: AsteroidsRepository
): ViewModel() {

    private val _asteroidsStateFlow = MutableStateFlow(AsteroidsUiState())
    val asteroidsStateFlow = _asteroidsStateFlow.asStateFlow()

    var showOnlyHazardous = false

    data class AsteroidsUiState(
        val asteroids: PagingData<Pair<String, List<AsteroidRepo>>> = PagingData.empty(),
        val sortParamStringRes: Int? = null,
    )

    fun getAsteroids(startDate: String, endDate: String, sortByDesc: Boolean = false) = viewModelScope.launch {
        asteroidsRepository.getAsteroids(
            startDate, endDate, sortByDesc
        ).cachedIn(viewModelScope).flowOn(Dispatchers.IO).collectLatest { pagingData ->
            if (showOnlyHazardous) {
                _asteroidsStateFlow.update {
                    it.copy(
                        asteroids = pagingData.map { pair ->
                            pair.first to pair.second.filter { asteroid ->
                                asteroid.isPotentiallyHazardousAsteroid ?: false
                            }
                        }
                    )
                }
            } else {
                _asteroidsStateFlow.update {
                    it.copy(
                        asteroids = pagingData
                    )
                }
            }
        }
    }


}