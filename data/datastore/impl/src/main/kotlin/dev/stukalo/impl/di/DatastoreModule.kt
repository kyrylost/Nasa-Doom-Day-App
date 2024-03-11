package dev.stukalo.impl.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.stukalo.datastore.PreferencesManager
import dev.stukalo.impl.PreferencesManagerImpl

@Module
@InstallIn(SingletonComponent::class)
interface DatastoreModule {
    @Binds
    fun bindsPreferencesManager(preferencesManagerImpl: PreferencesManagerImpl): PreferencesManager
}
