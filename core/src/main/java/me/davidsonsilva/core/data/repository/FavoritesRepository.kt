package me.davidsonsilva.core.data.repository

import kotlinx.coroutines.flow.Flow
import me.davidsonsilva.core.domain.model.Character

interface FavoritesRepository {

    fun getAll(): Flow<List<Character>>

    suspend fun saveFavorite(character: Character)

    suspend fun deleteFavorite(character: Character)
}