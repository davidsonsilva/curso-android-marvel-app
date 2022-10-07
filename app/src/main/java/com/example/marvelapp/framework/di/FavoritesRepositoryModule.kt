package com.example.marvelapp.framework.di

import com.example.marvelapp.framework.FavoritesRepositoryImpl
import com.example.marvelapp.framework.local.RoomFavoritesDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.davidsonsilva.core.data.repository.FavoritesLocalDataSource
import me.davidsonsilva.core.data.repository.FavoritesRepository

@Module
@InstallIn(SingletonComponent::class)
interface FavoritesRepositoryModule {

    @Binds
    fun bindFavoritesRepository(repository: FavoritesRepositoryImpl): FavoritesRepository

    @Binds
    fun bindLocalDataSource(
        dataSource: RoomFavoritesDataSource
    ): FavoritesLocalDataSource
}