package com.example.marvelapp.framework.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import me.davidsonsilva.core.usecase.base.AppCoroutinesDispatchers

@Module
@InstallIn(SingletonComponent::class)
object CoroutinesModule {

    @Provides
    fun providesDispatchers() = AppCoroutinesDispatchers(
        Dispatchers.IO,
        Dispatchers.Default,
        Dispatchers.Main
    )
}