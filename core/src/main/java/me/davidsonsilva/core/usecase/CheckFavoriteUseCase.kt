package me.davidsonsilva.core.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import me.davidsonsilva.core.base.CoroutinesDispatchers
import me.davidsonsilva.core.base.ResultStatus
import me.davidsonsilva.core.base.UseCase
import me.davidsonsilva.core.data.repository.FavoritesRepository
import javax.inject.Inject

interface CheckFavoriteUseCase {

    operator fun invoke(params:Params): Flow<ResultStatus<Boolean>>

    data class Params(val characterId:Int)

}

class CheckFavoriteUseCaseImpl @Inject constructor(
    private val repository: FavoritesRepository,
    private val dispatchers: CoroutinesDispatchers
) :UseCase<CheckFavoriteUseCase.Params, Boolean>(), CheckFavoriteUseCase {

    override suspend fun doWork(params: CheckFavoriteUseCase.Params): ResultStatus<Boolean> {
        return withContext(dispatchers.io()){
            val isFavorite = repository.isFavorite(params.characterId)
            ResultStatus.Success(isFavorite)
        }
    }
}