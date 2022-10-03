package com.example.marvelapp.framework

import androidx.paging.PagingSource
import com.example.marvelapp.framework.paging.CharactersPagingSource
import me.davidsonsilva.core.data.repository.CharactersRemoteDataSource
import me.davidsonsilva.core.data.repository.CharactersRepository
import me.davidsonsilva.core.domain.model.Character
import me.davidsonsilva.core.domain.model.Comic
import me.davidsonsilva.core.domain.model.Event
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(
    private val remoteDataSource:CharactersRemoteDataSource
) : CharactersRepository {

    override fun getCharacters(query: String): PagingSource<Int, Character> {
        return CharactersPagingSource(remoteDataSource = remoteDataSource,  query = query)
    }

    override suspend fun getComics(characterId: Int): List<Comic> {
        return remoteDataSource.fetchComics(characterId)
    }

    override suspend fun getEvents(characterId: Int): List<Event> {
        return remoteDataSource.fetchEvents(characterId)
    }

}