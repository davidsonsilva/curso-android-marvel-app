package me.davidsonsilva.core.data.repository

import kotlinx.coroutines.flow.Flow
import me.davidsonsilva.core.domain.model.Character

interface FavoritesLocalDataSource {

    fun getAll(): Flow<List<Character>>

    suspend fun save(character: Character)

    suspend fun delete(character: Character)

}