package dev.stukalo.asteroids

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
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

    var sortOrder = 0

    private val _asteroidsStateFlow = MutableStateFlow(AsteroidsUiState())
    val asteroidsStateFlow = _asteroidsStateFlow.asStateFlow()

    data class AsteroidsUiState(
        val asteroids: PagingData<Pair<String, List<AsteroidRepo>>> = PagingData.empty(),
        val sortParamStringRes: Int? = null,
    )

    fun getAsteroids(startDate: String, endDate: String) = viewModelScope.launch {
        asteroidsRepository.getAsteroids(
            startDate, endDate, sortOrder
        ).cachedIn(viewModelScope).flowOn(Dispatchers.IO).collectLatest { pagingData ->
            _asteroidsStateFlow.update {
                it.copy(
                    asteroids = pagingData
                )
            }
        }
    }


}