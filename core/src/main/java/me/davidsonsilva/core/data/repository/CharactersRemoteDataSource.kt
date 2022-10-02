package me.davidsonsilva.core.data.repository

import me.davidsonsilva.core.domain.model.CharacterPaging
import me.davidsonsilva.core.domain.model.Comic

interface CharactersRemoteDataSource {

    suspend fun fetchCharacters(queries:Map<String,String>): CharacterPaging

    suspend fun fetchComics(characterId: Int): List<Comic>
}