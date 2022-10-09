package me.davidsonsilva.core.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import me.davidsonsilva.core.base.CoroutinesDispatchers
import me.davidsonsilva.core.base.FlowUseCase
import me.davidsonsilva.core.data.repository.FavoritesRepository
import me.davidsonsilva.core.domain.model.Character
import javax.inject.Inject

interface GetFavoritesUseCase {
    suspend operator fun invoke(params: Unit = Unit): Flow<List<Character>>
}

class GetFavoritesUseCaseImpl @Inject constructor(
    private val favoritesRepository: FavoritesRepository,
    private val dispatchers: CoroutinesDispatchers
) : FlowUseCase<Unit, List<Character>>(), GetFavoritesUseCase {
    override suspend fun createFlowObservable(params: Unit): Flow<List<Character>> {
       return withContext(dispatchers.io()) {
           favoritesRepository.getAll()
       }
    }
}
