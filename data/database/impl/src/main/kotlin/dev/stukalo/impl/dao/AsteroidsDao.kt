package dev.stukalo.impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.stukalo.database.model.AsteroidDb
import kotlinx.coroutines.flow.Flow

@Dao
interface AsteroidsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAsteroid(asteroidDb: AsteroidDb): Long

    @Query("SELECT * FROM asteroids WHERE id = :id")
    fun getAsteroidById(id: String?): AsteroidDb?

    @Query("SELECT * FROM asteroids")
    fun getAsteroids(): Flow<List<AsteroidDb>?>

    @Query("DELETE FROM asteroids WHERE id = :id")
    fun deleteAsteroid(id: String)
}