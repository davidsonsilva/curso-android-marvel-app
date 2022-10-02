package me.davidsonsilva.core.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import me.davidsonsilva.core.data.repository.CharactersRepository
import me.davidsonsilva.core.domain.model.Comic
import me.davidsonsilva.core.usecase.base.ResultStatus
import me.davidsonsilva.core.usecase.base.UseCase
import javax.inject.Inject

interface GetComicsUseCase {
    data class GetComicsParams(val characterId:Int)
    operator fun invoke(params: GetComicsParams):Flow<ResultStatus<List<Comic>>>
}

class GetComicsUseCaseImpl @Inject constructor(
    private val repository: CharactersRepository
): GetComicsUseCase, UseCase<GetComicsUseCase.GetComicsParams,List<Comic>>() {

    override suspend fun doWork(
        params: GetComicsUseCase.GetComicsParams
    ): ResultStatus<List<Comic>> {
        val comics = repository.getComics(params.characterId)
        return ResultStatus.Success(comics)
    }

}

