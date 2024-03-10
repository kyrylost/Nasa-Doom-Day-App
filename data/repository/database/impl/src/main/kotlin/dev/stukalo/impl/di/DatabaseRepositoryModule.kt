package dev.stukalo.impl.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.stukalo.database.datasource.AsteroidsDataSource
import dev.stukalo.database.repo.FavoriteAsteroidsRepository
import dev.stukalo.impl.FavoriteAsteroidsRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseRepositoryModule {
    @Provides
    @Singleton
    fun provideFavoriteAsteroidsRepository(asteroidsDataSource: AsteroidsDataSource): FavoriteAsteroidsRepository {
        return FavoriteAsteroidsRepositoryImpl(asteroidsDataSource)
    }
}
