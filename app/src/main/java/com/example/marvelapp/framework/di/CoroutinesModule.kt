package com.example.marvelapp.framework.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.davidsonsilva.core.base.AppCoroutinesDispatchers
import me.davidsonsilva.core.base.CoroutinesDispatchers

@Module
@InstallIn(SingletonComponent::class)
interface CoroutinesModule {

    @Binds
    fun bindDispatchers(dispatchers: AppCoroutinesDispatchers) : CoroutinesDispatchers

}