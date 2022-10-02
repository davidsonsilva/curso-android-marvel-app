package me.davidsonsilva.core.data.repository

import androidx.paging.PagingSource
import me.davidsonsilva.core.domain.model.Character
import me.davidsonsilva.core.domain.model.Comic

interface CharactersRepository {

    fun getCharacters(query: String): PagingSource<Int, Character>

    suspend fun getComics(characterId: Int): List<Comic>
}