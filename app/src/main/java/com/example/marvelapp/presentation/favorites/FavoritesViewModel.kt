package com.example.marvelapp.presentation.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import me.davidsonsilva.core.base.CoroutinesDispatchers
import me.davidsonsilva.core.usecase.GetFavoritesUseCase
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val coroutinesDispatchers: CoroutinesDispatchers
): ViewModel() {

    private val action = MutableLiveData<Action>()
    val state : LiveData<UiState> = action.switchMap { action ->
        liveData(coroutinesDispatchers.main()) {
            when (action) {
                is Action.GetAll -> {
                    getFavoritesUseCase.invoke()
                        .catch {
                            emit(UiState.ShowEmpty)
                        }
                        .collect {
                            val items = it.map{ characters ->
                                FavoriteItem(
                                    characters.id,
                                    characters.name,
                                    characters.imageUrl
                                )
                            }
                            val uiState = if(items.isEmpty()){
                                UiState.ShowEmpty
                            } else UiState.ShowFavorites(items)

                            emit(uiState)
                        }
                }
            }
        }
    }

    fun getAll() {
        action.value = Action.GetAll
    }

    sealed class UiState {
        data class ShowFavorites(val favorites: List<FavoriteItem> ) : UiState()
        object ShowEmpty : UiState()
    }

    sealed class Action {
        object GetAll : Action()
    }
}