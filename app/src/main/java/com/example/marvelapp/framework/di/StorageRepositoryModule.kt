package com.example.marvelapp.framework.di

import com.example.marvelapp.framework.StorageRepositoryImpl
import com.example.marvelapp.framework.local.DataStorePreferencesDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.davidsonsilva.core.data.repository.StorageLocalDataSource
import me.davidsonsilva.core.data.repository.StorageRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface StorageRepositoryModule {

    @Binds
    fun bindStorageRepository(repository: StorageRepositoryImpl): StorageRepository

    @Singleton
    @Binds
    fun bindStorageLocalDataSource(dataSource: DataStorePreferencesDataSource): StorageLocalDataSource
}