package me.davidsonsilva.core.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import me.davidsonsilva.core.base.CoroutinesDispatchers
import me.davidsonsilva.core.base.ResultStatus
import me.davidsonsilva.core.base.UseCase
import me.davidsonsilva.core.data.repository.FavoritesRepository
import me.davidsonsilva.core.domain.model.Character
import javax.inject.Inject

interface AddFavoriteUseCase {

    operator fun invoke(params:Params): Flow<ResultStatus<Unit>>

    data class Params(val characterId:Int, val name:String, val imageUrl:String)

}

class AddFavoriteUseCaseImpl @Inject constructor(
  private val repository:FavoritesRepository,
  private val dispatchers:CoroutinesDispatchers
) : UseCase<AddFavoriteUseCase.Params, Unit>(), AddFavoriteUseCase {
    override suspend fun doWork(params: AddFavoriteUseCase.Params): ResultStatus<Unit> {
        return withContext(dispatchers.io()) {
            repository.saveFavorite(
                Character(params.characterId,params.name,params.imageUrl)
            )
            ResultStatus.Success(Unit)
        }
    }
}
