package com.example.marvelapp.framework

import kotlinx.coroutines.flow.Flow
import me.davidsonsilva.core.data.repository.FavoritesLocalDataSource
import me.davidsonsilva.core.data.repository.FavoritesRepository
import me.davidsonsilva.core.domain.model.Character
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(
    private val favoritesLocalDataSource: FavoritesLocalDataSource
):FavoritesRepository {

    override fun getAll(): Flow<List<Character>> {
        return favoritesLocalDataSource.getAll()
    }

    override suspend fun isFavorite(characterId: Int): Boolean {
        return favoritesLocalDataSource.isFavorite(characterId)
    }

    override suspend fun saveFavorite(character: Character) {
        favoritesLocalDataSource.save(character = character)
    }

    override suspend fun deleteFavorite(character: Character) {
        favoritesLocalDataSource.delete(character = character)
    }
}