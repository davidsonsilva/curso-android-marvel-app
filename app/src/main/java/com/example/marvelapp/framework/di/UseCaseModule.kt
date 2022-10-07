package com.example.marvelapp.framework.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import me.davidsonsilva.core.usecase.GetCharactersUseCase
import me.davidsonsilva.core.usecase.GetCharactersUseCaseImpl
import me.davidsonsilva.core.usecase.GetCharacterCategoriesUseCase
import me.davidsonsilva.core.usecase.GetCharacterCategoriesUseCaseImpl
import me.davidsonsilva.core.usecase.AddFavoriteUseCaseImpl
import me.davidsonsilva.core.usecase.AddFavoriteUseCase
import me.davidsonsilva.core.usecase.CheckFavoriteUseCaseImpl
import me.davidsonsilva.core.usecase.CheckFavoriteUseCase


@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {

    @Binds
    fun bindGetCharactersUseCase(useCAse: GetCharactersUseCaseImpl):GetCharactersUseCase

    @Binds
    fun bindsGetComicsUseCase(useCase: GetCharacterCategoriesUseCaseImpl): GetCharacterCategoriesUseCase

    @Binds
    fun bindCheckFavoriteUseCase(useCase: CheckFavoriteUseCaseImpl): CheckFavoriteUseCase

    @Binds
    fun bindAddFavoriteUseCase(useCase:AddFavoriteUseCaseImpl): AddFavoriteUseCase
}