package com.example.marvelapp.presentation.characters

import androidx.paging.PagingData
import com.example.marvelapp.base.BaseTest
import com.example.testing.MainCoroutineRule
import com.example.testing.model.CharacterFactory
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import me.davidsonsilva.core.usecase.GetCharactersUseCase
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


class CharactersViewModelTest: BaseTest() {

    @Mock
    lateinit var  charactersUseCase: GetCharactersUseCase
    private lateinit var charactersViewModel: CharactersViewModel
    private val charactersFactory = CharacterFactory()
    private val pagingDataCharacter = PagingData.from(
        listOf(
            charactersFactory.create(CharacterFactory.Hero.ThreeDMan),
            charactersFactory.create(CharacterFactory.Hero.ABomb)
        )
    )

    @Before
    fun setup(){
        charactersViewModel = CharactersViewModel(charactersUseCase)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should validate the paging data object values when calling charactersPagingData`()
    = runTest {

        whenever(
            charactersUseCase.invoke(any())
        ).thenReturn(
            flowOf(
                pagingDataCharacter
            )
        )

        val result = charactersViewModel.charactersPagingData("")

        assertEquals(1, result.count())

    }

    @ExperimentalCoroutinesApi
    @Test(expected = RuntimeException::class)
    fun  `should throw an exception when the calling to the use case returns an exception`() = runTest {
        whenever(charactersUseCase.invoke(any()))
            .thenThrow(RuntimeException())
        charactersViewModel.charactersPagingData("")
    }

}