package me.davidsonsilva.core.usecase

import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import me.davidsonsilva.core.data.repository.CharactersRepository
import me.davidsonsilva.core.domain.model.Comic
import me.davidsonsilva.core.domain.model.Event
import me.davidsonsilva.core.usecase.base.AppCoroutinesDispatchers
import me.davidsonsilva.core.usecase.base.ResultStatus
import me.davidsonsilva.core.usecase.base.UseCase
import javax.inject.Inject

interface GetCharacterCategoryUseCase {
    data class GetComicsParams(val characterId:Int)
    operator fun invoke(params: GetComicsParams):Flow<ResultStatus<Pair<List<Comic>,List<Event>>>>
}

class GetCharacterCategoryUseCaseImpl @Inject constructor(
    private val repository: CharactersRepository,
    private val dispatchers: AppCoroutinesDispatchers
): GetCharacterCategoryUseCase, UseCase<GetCharacterCategoryUseCase.GetComicsParams,Pair<List<Comic>,List<Event>>>() {

    override suspend fun doWork(
        params: GetCharacterCategoryUseCase.GetComicsParams
    ): ResultStatus<Pair<List<Comic>, List<Event>>> {
        return withContext(dispatchers.io) {
            val comicsDeferred = async { repository.getComics(params.characterId) }
            val eventsDeferred = async { repository.getEvents(params.characterId) }

            val comics = comicsDeferred.await()
            val events = eventsDeferred.await()

            ResultStatus.Success(comics to events)
        }
    }

}

