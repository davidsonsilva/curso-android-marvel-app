package me.davidsonsilva.core.data.repository

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow
import me.davidsonsilva.core.domain.model.Character
import me.davidsonsilva.core.domain.model.Comic
import me.davidsonsilva.core.domain.model.Event

interface CharactersRepository {

    fun getCharacters(query: String): PagingSource<Int, Character>

    suspend fun getComics(characterId: Int): List<Comic>

    suspend fun getEvents(characterId: Int): List<Event>

    fun getCachedCharacters(query: String, orderBy:String, pagingConfig: PagingConfig):
            Flow<PagingData<Character>>

}