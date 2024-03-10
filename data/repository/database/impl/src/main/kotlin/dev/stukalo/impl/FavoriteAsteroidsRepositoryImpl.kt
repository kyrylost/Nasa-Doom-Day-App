package dev.stukalo.impl

import dev.stukalo.database.datasource.AsteroidsDataSource
import dev.stukalo.database.repo.FavoriteAsteroidsRepository
import dev.stukalo.impl.mapper.mapToAsteroidDb
import dev.stukalo.impl.mapper.mapToAsteroidRepo
import dev.stukalo.repository.model.AsteroidRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteAsteroidsRepositoryImpl @Inject constructor(
    private val asteroidsDataSource: AsteroidsDataSource
): FavoriteAsteroidsRepository {

    override suspend fun insertAsteroid(asteroidRepo: AsteroidRepo) =
        asteroidsDataSource.insertAsteroid(asteroidRepo.mapToAsteroidDb()!!)

    override fun getAsteroidById(id: String?) =
        asteroidsDataSource.getAsteroidById(id)?.mapToAsteroidRepo()

    override fun getFavoriteAsteroids(): Flow<List<AsteroidRepo>?> =
        asteroidsDataSource.getAsteroids().map { listOfAsteroidDb ->
            listOfAsteroidDb?.map {
                it.mapToAsteroidRepo()
            }
        }.flowOn(Dispatchers.IO)

    override fun deleteAsteroid(id: String) = asteroidsDataSource.deleteAsteroid(id)
}
