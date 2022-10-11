package me.davidsonsilva.core.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import me.davidsonsilva.core.base.CoroutinesDispatchers
import me.davidsonsilva.core.base.ResultStatus
import me.davidsonsilva.core.base.UseCase
import me.davidsonsilva.core.data.mapper.SortingMapper
import me.davidsonsilva.core.data.repository.FavoritesRepository
import me.davidsonsilva.core.data.repository.StorageRepository
import me.davidsonsilva.core.domain.model.Character
import javax.inject.Inject

interface SaveCharactersSortingUseCase {

    operator fun invoke(params:Params): Flow<ResultStatus<Unit>>

    data class Params(val sortingPair: Pair<String,String>)

}

class SaveCharactersSortingUseCaseImpl @Inject constructor(
    private val storageRepository: StorageRepository,
    private val sortingMapper: SortingMapper,
    private val dispatchers: CoroutinesDispatchers
) : UseCase<SaveCharactersSortingUseCase.Params, Unit>(), SaveCharactersSortingUseCase {

    override suspend fun doWork(params: SaveCharactersSortingUseCase.Params): ResultStatus<Unit> {
        return withContext(dispatchers.io()) {
            storageRepository.saveSorting(sorting = sortingMapper.mapFromPair(params.sortingPair))
            ResultStatus.Success(Unit)
        }
    }
}
