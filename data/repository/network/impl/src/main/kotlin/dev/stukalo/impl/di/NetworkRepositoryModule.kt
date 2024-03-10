package dev.stukalo.impl.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.stukalo.impl.AsteroidsRepositoryImpl
import dev.stukalo.network.source.AsteroidsNetSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkRepositoryModule {
    @Provides
    @Singleton
    fun provideAsteroidsRepository(asteroidsNetSource: AsteroidsNetSource): dev.stukalo.network.repo.AsteroidsRepository {
        return AsteroidsRepositoryImpl(asteroidsNetSource)
    }
}
