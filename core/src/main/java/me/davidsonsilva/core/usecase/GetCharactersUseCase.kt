package me.davidsonsilva.core.usecase

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import me.davidsonsilva.core.base.PagingUseCase
import me.davidsonsilva.core.data.repository.CharactersRepository
import me.davidsonsilva.core.domain.model.Character
import me.davidsonsilva.core.usecase.GetCharactersUseCase.GetCharactersParams
import javax.inject.Inject

interface GetCharactersUseCase {
    data class GetCharactersParams(val query: String, val pagingConfig: PagingConfig)

    operator fun invoke(params: GetCharactersParams): Flow<PagingData<Character>>
}

class GetCharactersUseCaseImpl @Inject constructor(
    private val charactersRepository: CharactersRepository
) : PagingUseCase<GetCharactersParams, Character>(),
    GetCharactersUseCase {

/*    override fun createFlowObservable(params: GetCharactersParams): Flow<PagingData<Character>> {
        val pagingSource = charactersRepository.getCharacters(params.query)
        return Pager(config = params.pagingConfig) {
            pagingSource
        }.flow
    }*/

    override fun createFlowObservable(params: GetCharactersParams): Flow<PagingData<Character>> {
        return charactersRepository.getCachedCharacters(params.query, params.pagingConfig)
    }
}