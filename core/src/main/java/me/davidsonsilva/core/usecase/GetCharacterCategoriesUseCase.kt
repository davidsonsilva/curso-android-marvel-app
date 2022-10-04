package me.davidsonsilva.core.usecase

import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import me.davidsonsilva.core.base.CoroutinesDispatchers
import me.davidsonsilva.core.base.ResultStatus
import me.davidsonsilva.core.base.UseCase
import me.davidsonsilva.core.data.repository.CharactersRepository
import me.davidsonsilva.core.domain.model.Comic
import me.davidsonsilva.core.domain.model.Event
import javax.inject.Inject

interface GetCharacterCategoriesUseCase {
    data class GetCategoriesParams(val characterId:Int)
    operator fun invoke(params: GetCategoriesParams):Flow<ResultStatus<Pair<List<Comic>,List<Event>>>>
}

class GetCharacterCategoriesUseCaseImpl @Inject constructor(
    private val repository: CharactersRepository,
    private val dispatchers: CoroutinesDispatchers
): GetCharacterCategoriesUseCase, UseCase<GetCharacterCategoriesUseCase.GetCategoriesParams,
        Pair<List<Comic>,List<Event>>>() {

    override suspend fun doWork(
        params: GetCharacterCategoriesUseCase.GetCategoriesParams
    ): ResultStatus<Pair<List<Comic>, List<Event>>> {
        return withContext(dispatchers.io()) {
            val comicsDeferred = async { repository.getComics(params.characterId) }
            val eventsDeferred = async { repository.getEvents(params.characterId) }

            val comics = comicsDeferred.await()
            val events = eventsDeferred.await()

            ResultStatus.Success(comics to events)
        }
    }

}

