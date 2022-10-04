package me.davidsonsilva.core.usecase

import com.example.testing.MainCoroutineRule
import com.example.testing.model.CharacterFactory
import com.example.testing.model.ComicFactory
import com.example.testing.model.EventFactory
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import me.davidsonsilva.core.base.BaseTest
import me.davidsonsilva.core.base.ResultStatus
import me.davidsonsilva.core.data.repository.CharactersRepository
import me.davidsonsilva.core.domain.model.Comic
import me.davidsonsilva.core.domain.model.Event
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetCharacterCategoriesUseCaseImplTest: BaseTest() {

    @Mock
    lateinit var repository: CharactersRepository

    private val character = CharacterFactory().create(CharacterFactory.Hero.ThreeDMan)
    private val comics = listOf<Comic>(ComicFactory().create(ComicFactory.FakeComic.FakeComic1))
    private val events = listOf<Event>(EventFactory().create(EventFactory.FakeEvent.FakeEvent1))

    private lateinit var characterCategoryUseCase: GetCharacterCategoriesUseCase

    @Before
    fun setup() {
        characterCategoryUseCase = GetCharacterCategoriesUseCaseImpl(
            repository,
            MainCoroutineRule().testDispatcherProvider
        )
    }

    @Test
    fun `should return Success from ResultStatus when get both requests return success`() =
        runTest {
            //Arrange
            whenever(repository.getComics(characterId = character.id)).thenReturn(comics)
            whenever(repository.getEvents(characterId = character.id)).thenReturn(events)

            //Act
            val result = characterCategoryUseCase.invoke(
                GetCharacterCategoriesUseCase.GetCategoriesParams(
                    characterId = character.id)
            )

            //Assert
            val resultLists = result.toList()
            assertEquals( ResultStatus.Loading, resultLists[0])
            assertTrue(resultLists[1] is ResultStatus.Success)
            assertEquals(resultLists[1],ResultStatus.Success(comics to events))
        }

    @Test
    fun `should return Error from ResultStatus when get events request returns error`() =
        runTest {
            //Arrange
            whenever(repository.getComics(characterId = character.id)).thenAnswer{
                throw Throwable()
            }


            //Act
            val result = characterCategoryUseCase.invoke(
                GetCharacterCategoriesUseCase.GetCategoriesParams(
                    characterId = character.id)
            )

            //Assert
            val resultLists = result.toList()
            assertEquals( ResultStatus.Loading, resultLists[0])
            assertTrue(resultLists[1] is ResultStatus.Error)

        }

    @Test
    fun `should return Error from ResultStatus when get comics request returns error`() =
        runTest {
            //Arrange
            whenever(repository.getEvents(characterId = character.id)).thenAnswer{
                throw Throwable()
            }


            //Act
            val result = characterCategoryUseCase.invoke(
                GetCharacterCategoriesUseCase.GetCategoriesParams(
                    characterId = character.id)
            )

            //Assert
            val resultLists = result.toList()
            assertEquals( ResultStatus.Loading, resultLists[0])
            assertTrue(resultLists[1] is ResultStatus.Error)
        }

}