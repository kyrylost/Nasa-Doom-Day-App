package dev.stukalo.database.repo

import dev.stukalo.repository.model.AsteroidRepo
import kotlinx.coroutines.flow.Flow

interface FavoriteAsteroidsRepository {
    suspend fun insertAsteroid(asteroidRepo: AsteroidRepo): Long

    fun getAsteroidById(id: String?): AsteroidRepo?

    fun getFavoriteAsteroids(): Flow<List<AsteroidRepo>?>

    fun deleteAsteroid(id: String)

    fun updateIsShownField(
        id: String,
        isShown: Boolean,
    )
}
