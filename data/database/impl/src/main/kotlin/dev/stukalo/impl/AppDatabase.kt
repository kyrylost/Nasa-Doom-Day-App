package dev.stukalo.impl

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.stukalo.database.model.AsteroidDb
import dev.stukalo.impl.dao.AsteroidsDao

@Database(
    entities = [
        AsteroidDb::class,
    ],
    version = 1,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun asteroidsDao(): AsteroidsDao
}
