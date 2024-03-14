package dev.stukalo.impl.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.stukalo.database.datasource.AsteroidsDataSource
import dev.stukalo.impl.AppDatabase
import dev.stukalo.impl.dao.AsteroidsDao
import dev.stukalo.impl.datasource.AsteroidsDataSourceImpl

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {
    @Provides
    fun provideAppDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            "Asteroids Database",
        ).build()
    }

    @Provides
    fun provideAssetsDao(appDatabase: AppDatabase): AsteroidsDao {
        return appDatabase.asteroidsDao()
    }

    @Provides
    fun provideAssetsDataSource(asteroidsDao: AsteroidsDao): AsteroidsDataSource {
        return AsteroidsDataSourceImpl(asteroidsDao)
    }
}
