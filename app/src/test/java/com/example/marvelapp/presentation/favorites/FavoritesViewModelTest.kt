package com.example.marvelapp.presentation.favorites

import androidx.lifecycle.Observer
import com.example.marvelapp.base.BaseTest
import com.example.testing.model.CharacterFactory
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import me.davidsonsilva.core.domain.model.Character
import me.davidsonsilva.core.usecase.GetFavoritesUseCase
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class FavoritesViewModelTest: BaseTest() {

    private lateinit var favoritesViewModel: FavoritesViewModel
    @Mock
    private lateinit var useCase: GetFavoritesUseCase
    @Mock
    private lateinit var uiStateObserver:Observer<FavoritesViewModel.UiState>

    private val character = CharacterFactory().create(CharacterFactory.Hero.ThreeDMan)
    private val favoriteItems = listOf<FavoriteItem>(
        FavoriteItem(character.id,character.name, character.imageUrl)
    )

    @Before
    fun setup(){
        favoritesViewModel  = FavoritesViewModel(
            useCase,
            mainCoroutineRule.testDispatcherProvider
        ).apply {
            state.observeForever(uiStateObserver)
        }
    }

    @Test
    fun `should notify uiState with Success when getAll favorites`() = runTest {
        //Arrange
        whenever(useCase.invoke(any()))
            .thenReturn(
               flowOf(listOf<Character>(character))
            )
        //Act
        favoritesViewModel.getAll()
         val uiState = favoritesViewModel.state.value
        //Assert
        assertEquals(uiState, FavoritesViewModel.UiState.ShowFavorites(favoriteItems))
    }

    @Test
    fun `should notify uiState with Empty when getAll favorites`() = runTest {
        //Arrange
        whenever(useCase.invoke(any()))
            .thenReturn(
                flowOf(emptyList())
            )
        //Act
        favoritesViewModel.getAll()
        val uiState = favoritesViewModel.state.value
        //Assert
        assertEquals(uiState, FavoritesViewModel.UiState.ShowEmpty)
    }

}