package dev.stukalo.impl.datasource

import dev.stukalo.database.datasource.AsteroidsDataSource
import dev.stukalo.impl.dao.AsteroidsDao
import dev.stukalo.database.model.AsteroidDb
import javax.inject.Inject

class AsteroidsDataSourceImpl @Inject constructor(
    private val asteroidsDao: AsteroidsDao,
): AsteroidsDataSource {
    override suspend fun insertAsteroid(asteroidDb: AsteroidDb) = asteroidsDao.insertAsteroid(asteroidDb)

    override fun getAsteroidById(id: String?) = asteroidsDao.getAsteroidById(id)

    override fun getAsteroids() = asteroidsDao.getAsteroids()

    override fun deleteAsteroid(id: String) = asteroidsDao.deleteAsteroid(id)
}