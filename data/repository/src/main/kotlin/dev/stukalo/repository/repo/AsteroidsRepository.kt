package dev.stukalo.repository.repo

import androidx.paging.PagingData
import dev.stukalo.repository.model.AsteroidRepo
import kotlinx.coroutines.flow.Flow

interface AsteroidsRepository {
    fun getAsteroids(startDate: String, endDate: String, sortOrder: Int): Flow<PagingData<Pair<String, List<AsteroidRepo>>>>
}
