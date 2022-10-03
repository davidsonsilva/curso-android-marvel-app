package com.example.marvelapp.framework.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import me.davidsonsilva.core.usecase.GetCharactersUseCase
import me.davidsonsilva.core.usecase.GetCharactersUseCaseImpl
import me.davidsonsilva.core.usecase.GetCharacterCategoryUseCase
import me.davidsonsilva.core.usecase.GetCharacterCategoryUseCaseImpl


@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {

    @Binds
    fun bindGetCharactersUseCase(useCAse: GetCharactersUseCaseImpl):GetCharactersUseCase

    @Binds
    fun bindsGetComicsUseCase(useCase: GetCharacterCategoryUseCaseImpl): GetCharacterCategoryUseCase
}