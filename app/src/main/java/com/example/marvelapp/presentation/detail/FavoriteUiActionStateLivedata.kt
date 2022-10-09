package com.example.marvelapp.presentation.detail

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataScope
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.example.marvelapp.R
import com.example.marvelapp.presentation.extensions.watchStatus
import me.davidsonsilva.core.usecase.AddFavoriteUseCase
import me.davidsonsilva.core.usecase.CheckFavoriteUseCase
import me.davidsonsilva.core.usecase.RemoveFavoriteUseCase
import kotlin.coroutines.CoroutineContext

class FavoriteUiActionStateLivedata(
    private val coroutineContext: CoroutineContext,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val checkFavoriteUseCase: CheckFavoriteUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase
) {

    private val action = MutableLiveData<Action>()

    @set:VisibleForTesting
    var currentFavoriteIcon = R.drawable.ic_favorite_unchecked

    val state : LiveData<UiState> = action.switchMap {
        liveData(coroutineContext) {
            when(it){
                is Action.CheckFavorite -> {
                    checkFavoriteUseCase.invoke(
                        CheckFavoriteUseCase.Params(
                        characterId = it.characterId)
                    ).watchStatus(
                        success = { isFavorite ->
                            if (isFavorite){
                                currentFavoriteIcon =  R.drawable.ic_favorite_checked
                            }
                            emitFavoriteIcon()
                        },
                        error = {}
                    )
                }
                is Action.AddFavorite -> {
                    it.detailViewArgs.run {
                        addFavoriteUseCase.invoke(
                            AddFavoriteUseCase.Params(characterId, name, imageUrl)
                        ).watchStatus(
                            loading = {
                                emit(UiState.Loading)
                            },
                            success = {
                                currentFavoriteIcon = R.drawable.ic_favorite_checked
                                emitFavoriteIcon()
                            },
                            error = {
                                emit(UiState.Error(R.string.error_add_favorite))
                            }
                        )
                    }
                }
                is Action.RemoveFavorite -> {
                    it.detailViewArgs.run {
                        removeFavoriteUseCase.invoke(
                            RemoveFavoriteUseCase.Params(characterId, name, imageUrl)
                        ).watchStatus(
                            loading = {
                                emit(UiState.Loading)
                            },
                            success = {
                                currentFavoriteIcon = R.drawable.ic_favorite_unchecked
                                emitFavoriteIcon()
                            },
                            error = {
                                emit(UiState.Error(R.string.error_remove_favorite))
                            }
                        )
                    }
                }
            }
        }
    }

    private suspend fun LiveDataScope<UiState>.emitFavoriteIcon() {
        emit(UiState.Icon(currentFavoriteIcon))
    }

    fun checkFavorite(characterId: Int) {
         action.value = Action.CheckFavorite(characterId)
    }

    fun update(detailViewArgs: DetailViewArgs) {
        action.value = if (currentFavoriteIcon == R.drawable.ic_favorite_unchecked) {
            Action.AddFavorite(detailViewArgs)
        } else Action.RemoveFavorite(detailViewArgs)
    }

    sealed class UiState {
        object Loading: UiState()
        data class Icon(@DrawableRes val icon: Int):UiState()
        data class Error(@StringRes val messageResId: Int):UiState()
    }

    sealed class Action {
        data class CheckFavorite(val characterId: Int) : Action()
        data class AddFavorite(val detailViewArgs: DetailViewArgs) : Action()
        data class RemoveFavorite(val detailViewArgs: DetailViewArgs) : Action()
    }
}