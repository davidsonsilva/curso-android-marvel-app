package com.example.marvelapp.presentation.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.davidsonsilva.core.base.CoroutinesDispatchers
import me.davidsonsilva.core.domain.model.Character
import me.davidsonsilva.core.usecase.GetCharactersUseCase
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    coroutinesDispatchers: CoroutinesDispatchers
):ViewModel() {

    private val action = MutableLiveData<Action>()
    val state: LiveData<UiState> = action
        .distinctUntilChanged()
        .switchMap { action ->
            when (action) {
                is Action.Search -> {
                    getCharactersUseCase(
                        GetCharactersUseCase.GetCharactersParams(action.query, getPageConfig())
                    ).cachedIn(viewModelScope).map {
                        UiState.SearchResult(it)
                    }.asLiveData(coroutinesDispatchers.main())
                }
            }
        }

    fun charactersPagingData(query:String): Flow<PagingData<Character>>{
        return getCharactersUseCase(
            GetCharactersUseCase.GetCharactersParams(query, getPageConfig())
        ).cachedIn(viewModelScope)
    }

    private fun getPageConfig() = PagingConfig( pageSize = 20)

    fun searchCharacters(query: String = ""){
        action.value = Action.Search(query)
    }

    sealed class UiState {
        data class SearchResult(val data: PagingData<Character>) :UiState()
    }

    sealed class Action {
        data class Search(val query: String) :Action()
    }

}