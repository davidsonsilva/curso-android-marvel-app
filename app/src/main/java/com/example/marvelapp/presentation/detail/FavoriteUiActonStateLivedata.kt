package com.example.marvelapp.presentation.detail

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.example.marvelapp.R
import com.example.marvelapp.presentation.extensions.watchStatus
import me.davidsonsilva.core.usecase.AddFavoriteUseCase
import me.davidsonsilva.core.usecase.CheckFavoriteUseCase
import kotlin.coroutines.CoroutineContext

class FavoriteUiActonStateLivedata(
    private val coroutineContext: CoroutineContext,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val checkFavoriteUseCase: CheckFavoriteUseCase
) {

    private val action = MutableLiveData<Action>()

    val state : LiveData<UiState> = action.switchMap {
        liveData(coroutineContext) {
            when(it){
                is Action.CheckFavorite -> {
                    checkFavoriteUseCase.invoke(
                        CheckFavoriteUseCase.Params(
                        characterId = it.characterId)
                    ).watchStatus(
                        success = { isFavorite ->
                            val icon = if (isFavorite) R.drawable.ic_favorite_checked
                            else R.drawable.ic_favorite_unchecked
                            emit(UiState.Icon(icon))
                        },
                        error = {}
                    )
                }
                is Action.Update -> {
                    it.detailViewArgs.run {
                        addFavoriteUseCase.invoke(
                            AddFavoriteUseCase.Params(characterId, name, imageUrl)
                        ).watchStatus(
                            loading = {
                                emit(UiState.Loading)
                            },
                            success = {
                                emit(UiState.Icon(R.drawable.ic_favorite_checked))
                            },
                            error = {
                                emit(UiState.Error(R.string.error_add_favorite))
                            }
                        )
                    }
                }
            }
        }
    }

    fun checkFavorite(characterId: Int) {
         action.value = Action.CheckFavorite(characterId)
    }

    fun update(detailViewArgs: DetailViewArgs) {
        action.value = Action.Update(detailViewArgs)
    }

    sealed class UiState {
        object Loading: UiState()
        data class Icon(@DrawableRes val icon: Int):UiState()
        data class Error(@StringRes val messageResId: Int):UiState()
    }

    sealed class Action {
        data class CheckFavorite(val characterId: Int) : Action()
        data class Update(val detailViewArgs: DetailViewArgs) : Action()
    }
}