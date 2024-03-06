package dev.stukalo.impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import dev.stukalo.network.source.AsteroidsNetSource
import dev.stukalo.repository.repo.AsteroidsRepository
import javax.inject.Inject

class AsteroidsRepositoryImpl @Inject constructor(
    private val asteroidsNetSource: AsteroidsNetSource,
) : AsteroidsRepository {
    override fun getAsteroids(startDate: String, endDate: String, sortByDesc: Boolean) = Pager(
        config = PagingConfig(pageSize = 1),
        pagingSourceFactory = {
            AsteroidsPagingSource(asteroidsNetSource, startDate, endDate, sortByDesc)
        }
    ).flow
}