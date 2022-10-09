package com.example.marvelapp.presentation.detail

import androidx.lifecycle.Observer
import com.example.marvelapp.R
import com.example.marvelapp.base.BaseTest
import com.example.testing.model.CharacterFactory
import com.example.testing.model.ComicFactory
import com.example.testing.model.EventFactory
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.isA
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import me.davidsonsilva.core.base.ResultStatus
import me.davidsonsilva.core.domain.model.Comic
import me.davidsonsilva.core.usecase.AddFavoriteUseCase
import me.davidsonsilva.core.usecase.CheckFavoriteUseCase
import me.davidsonsilva.core.usecase.GetCharacterCategoriesUseCase
import me.davidsonsilva.core.usecase.RemoveFavoriteUseCase
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest:BaseTest(){

    @Mock
    lateinit var  getCharacterCategoriesUseCase: GetCharacterCategoriesUseCase

    @Mock
    private lateinit var  uiStateObserver: Observer<UiActionStateLiveData.UiState>

    @Mock
    private lateinit var  favoriteUiStateObserver: Observer<FavoriteUiActionStateLivedata.UiState>

    @Mock
    private lateinit var addFavoriteUseCase: AddFavoriteUseCase

    @Mock
    private lateinit var removeFavoriteUseCase: RemoveFavoriteUseCase

    @Mock
    private lateinit var checkFavoriteUseCase: CheckFavoriteUseCase

    private lateinit var detailViewModel: DetailViewModel

    private val character = CharacterFactory().create(CharacterFactory.Hero.ThreeDMan)
    private val comics = listOf(ComicFactory().create(ComicFactory.FakeComic.FakeComic1))
    private val events = listOf(EventFactory().create(EventFactory.FakeEvent.FakeEvent1))

    @Before
    fun setup(){
        detailViewModel = DetailViewModel(
            getCharacterCategoriesUseCase,
            checkFavoriteUseCase,
            addFavoriteUseCase,
            removeFavoriteUseCase,
            mainCoroutineRule.testDispatcherProvider
        ).apply {
            categories.state.observeForever(uiStateObserver)
            favorite.state.observeForever(favoriteUiStateObserver)
        }
    }

    @Test
    fun `should notify uiState with Success from UiState when get character categories returns success`() =
        runTest {
            // Arrange
            whenever(getCharacterCategoriesUseCase.invoke(any()))
                .thenReturn(
                    flowOf(ResultStatus.Success(comics to events))
                )

            //Act
            detailViewModel.categories.load(character.id)

            //Assert
            verify(uiStateObserver).onChanged(isA<UiActionStateLiveData.UiState.Success>())

            val uiStateSuccess = detailViewModel.categories.state.value as UiActionStateLiveData.UiState.Success
            val categoriesParentList = uiStateSuccess.detailParentList

            assertEquals(2, categoriesParentList.size)
            assertEquals(
                R.string.details_comics_category,
                categoriesParentList[0].categoryStringResId
            )
            assertEquals(
                R.string.details_events_category,
                categoriesParentList[1].categoryStringResId
            )
        }

    @Test
    fun `should notify uiState with Success from UiState when get character categories returns only comics`() =
        runTest {
            // Arrange
            whenever(getCharacterCategoriesUseCase.invoke(any()))
                .thenReturn(
                    flowOf(ResultStatus.Success(comics to emptyList()))
                )
            //Act
            detailViewModel.categories.load(character.id)

            //Assert
            verify(uiStateObserver).onChanged(isA<UiActionStateLiveData.UiState.Success>())

            val uiStateSuccess = detailViewModel.categories.state.value as UiActionStateLiveData.UiState.Success
            val categoriesParentList = uiStateSuccess.detailParentList

            assertEquals(1, categoriesParentList.size)
            assertEquals(
                R.string.details_comics_category,
                categoriesParentList[0].categoryStringResId
            )

    }

    @Test
    fun `should notify uiState with Success from UiState when get character categories returns only events`() =
        runTest{
            // Arrange
            whenever(getCharacterCategoriesUseCase.invoke(any()))
                .thenReturn(
                    flowOf(ResultStatus.Success(emptyList<Comic>() to events))
                )
            //Act
            detailViewModel.categories.load(character.id)

            //Assert
            verify(uiStateObserver).onChanged(isA<UiActionStateLiveData.UiState.Success>())

            val uiStateSuccess = detailViewModel.categories.state.value as UiActionStateLiveData.UiState.Success
            val categoriesParentList = uiStateSuccess.detailParentList

            assertEquals(1, categoriesParentList.size)
            assertEquals(
                R.string.details_events_category,
                categoriesParentList[0].categoryStringResId
            )
    }

    @Test
    fun `should notify uiState with Empty from UiState when get character categories returns an empty result list`() =
        runTest {
            // Arrange
            whenever(getCharacterCategoriesUseCase.invoke(any()))
                .thenReturn(
                    flowOf(ResultStatus.Success(emptyList<Comic>() to emptyList()))
                )
            //Act
            detailViewModel.categories.load(character.id)

            //Assert
            verify(uiStateObserver).onChanged(isA<UiActionStateLiveData.UiState.Empty>())

        }

    @Test
    fun `should notify uiState with Error from UiState when get character categories returns an exception`() =
        runTest {
            // Arrange
            whenever(getCharacterCategoriesUseCase.invoke(any()))
                .thenReturn(
                    flowOf(ResultStatus.Error(Throwable()))
                )
            //Act
            detailViewModel.categories.load(character.id)

            //Assert
            verify(uiStateObserver).onChanged(isA<UiActionStateLiveData.UiState.Error>())
        }

    @Test
    fun `should notify favorite_uiState with filled favorite icon when check favorite returns true`() =
        runTest {
            // Arrange
            whenever(checkFavoriteUseCase.invoke(any()))
                .thenReturn(
                    flowOf(ResultStatus.Success(true))
                )
            // Action
            detailViewModel.favorite.checkFavorite(any())

            // Assert
            verify(favoriteUiStateObserver).onChanged(
                isA<FavoriteUiActionStateLivedata.UiState.Icon>()
            )
            val uiState = detailViewModel.favorite.state.value as
                    FavoriteUiActionStateLivedata.UiState.Icon
            assertEquals(uiState.icon, R.drawable.ic_favorite_checked)
        }

    @Test
    fun `should notify favorite_uiState with not filled favorite icon when check favorite returns false`() =
        runTest {
            // Arrange
            whenever(checkFavoriteUseCase.invoke(any()))
                .thenReturn(
                    flowOf(ResultStatus.Success(false))
                )
            // Action
            detailViewModel.favorite.checkFavorite(any())

            // Assert
            verify(favoriteUiStateObserver).onChanged(
                isA<FavoriteUiActionStateLivedata.UiState.Icon>()
            )
            val uiState = detailViewModel.favorite.state.value as
                    FavoriteUiActionStateLivedata.UiState.Icon
            assertEquals(uiState.icon, R.drawable.ic_favorite_unchecked)
        }

    @Test
    fun `should notify favorite_uiState with filled favorite icon when current icon is unchecked`() =
        runTest {
            // Arrange
            whenever(addFavoriteUseCase.invoke(any()))
                .thenReturn(
                    flowOf(
                        ResultStatus.Success(Unit)
                    )
                )

            // Act
            detailViewModel.run {
                favorite.currentFavoriteIcon = R.drawable.ic_favorite_unchecked
                favorite.update(
                    DetailViewArgs(character.id, character.name, character.imageUrl)
                )
            }

            // Assert
            verify(favoriteUiStateObserver).onChanged(isA<FavoriteUiActionStateLivedata.UiState.Icon>())
            val uiState =
                detailViewModel.favorite.state.value as FavoriteUiActionStateLivedata.UiState.Icon
            assertEquals(R.drawable.ic_favorite_checked, uiState.icon)
        }

    @Test
    fun `should call remove and notify favorite_uiState with filled favorite icon when current icon is checked`() =
        runTest {
            // Arrange
            whenever(removeFavoriteUseCase.invoke(any()))
                .thenReturn(
                    flowOf(
                        ResultStatus.Success(Unit)
                    )
                )

            // Act
            detailViewModel.run {
                favorite.currentFavoriteIcon = R.drawable.ic_favorite_checked
                favorite.update(
                    DetailViewArgs(character.id, character.name, character.imageUrl)
                )
            }

            // Assert
            verify(favoriteUiStateObserver).onChanged(isA<FavoriteUiActionStateLivedata.UiState.Icon>())
            val uiState =
                detailViewModel.favorite.state.value as FavoriteUiActionStateLivedata.UiState.Icon
            assertEquals(R.drawable.ic_favorite_unchecked, uiState.icon)
        }
}