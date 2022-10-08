package com.example.marvelapp.presentation.detail

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import me.davidsonsilva.core.base.CoroutinesDispatchers
import me.davidsonsilva.core.usecase.AddFavoriteUseCase
import me.davidsonsilva.core.usecase.CheckFavoriteUseCase
import me.davidsonsilva.core.usecase.GetCharacterCategoriesUseCase
import me.davidsonsilva.core.usecase.RemoveFavoriteUseCase
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    getCharacterCategoriesUseCase: GetCharacterCategoriesUseCase,
    checkFavoriteUseCase: CheckFavoriteUseCase,
    addFavoriteUseCase: AddFavoriteUseCase,
    removeFavoriteUseCase: RemoveFavoriteUseCase,
    coroutinesDispatchers: CoroutinesDispatchers
): ViewModel(){

    val categories = UiActionStateLiveData(
        coroutinesDispatchers.main(),
        getCharacterCategoriesUseCase
    )

    val favorite = FavoriteUiActionStateLivedata(
        coroutinesDispatchers.main(),
        addFavoriteUseCase,
        checkFavoriteUseCase,
        removeFavoriteUseCase
    )

}