package me.davidsonsilva.core.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import me.davidsonsilva.core.base.CoroutinesDispatchers
import me.davidsonsilva.core.base.ResultStatus
import me.davidsonsilva.core.base.UseCase
import me.davidsonsilva.core.data.repository.FavoritesRepository
import me.davidsonsilva.core.domain.model.Character
import javax.inject.Inject

interface RemoveFavoriteUseCase {

    operator fun invoke(params:Params): Flow<ResultStatus<Unit>>

    data class Params(val characterId:Int, val name:String, val imageUrl:String)

}

class RemoveFavoriteUseCaseImpl @Inject constructor(
    private val repository: FavoritesRepository,
    private val dispatchers: CoroutinesDispatchers
) : UseCase<RemoveFavoriteUseCase.Params, Unit>(), RemoveFavoriteUseCase {
    override suspend fun doWork(params: RemoveFavoriteUseCase.Params): ResultStatus<Unit> {
        return withContext(dispatchers.io()) {
            repository.deleteFavorite(
                Character(params.characterId,params.name,params.imageUrl)
            )
            ResultStatus.Success(Unit)
        }
    }
}