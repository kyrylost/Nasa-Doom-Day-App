package dev.stukalo.database.datasource

import dev.stukalo.database.model.AsteroidDb
import kotlinx.coroutines.flow.Flow

interface AsteroidsDataSource {
    suspend fun insertAsteroid(asteroidDb: AsteroidDb): Long

    fun getAsteroidById(id: String?): AsteroidDb?

    fun getAsteroids(): Flow<List<AsteroidDb>?>

    fun deleteAsteroid(id: String)
}