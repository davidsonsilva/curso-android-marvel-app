package me.davidsonsilva.core.usecase

import androidx.paging.PagingConfig
import me.davidsonsilva.core.base.BaseTest
import com.example.testing.model.CharacterFactory
import com.example.testing.pagingsource.PagingSourceFactory
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import me.davidsonsilva.core.data.repository.CharactersRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetCharactersUseCaseImplTest: BaseTest() {

    private lateinit var getCharactersUseCase: GetCharactersUseCase

    @Mock
    lateinit var repository: CharactersRepository

    private val hero = CharacterFactory().create(CharacterFactory.Hero.ThreeDMan)
    private val fakePagingSource = PagingSourceFactory().create(listOf(hero))

    @Before
    fun setUp() {
        getCharactersUseCase = GetCharactersUseCaseImpl(repository)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should validate flow paging data creation when invoke from use case is called`() =
        runTest {
            whenever(repository.getCharacters(""))
                .thenReturn(fakePagingSource)
            val result = getCharactersUseCase.invoke(
                GetCharactersUseCase.GetCharactersParams(
                    "", pagingConfig = PagingConfig(pageSize = 20)))

            verify(repository).getCharacters("")

            assertNotNull(result.first())
        }
}