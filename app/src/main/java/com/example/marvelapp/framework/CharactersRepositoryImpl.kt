package com.example.marvelapp.framework

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.map
import com.example.marvelapp.framework.db.AppDatabase
import com.example.marvelapp.framework.paging.CharactersPagingSource
import com.example.marvelapp.framework.paging.CharactersRemoteMediator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.davidsonsilva.core.data.repository.CharactersRemoteDataSource
import me.davidsonsilva.core.data.repository.CharactersRepository
import me.davidsonsilva.core.domain.model.Character
import me.davidsonsilva.core.domain.model.Comic
import me.davidsonsilva.core.domain.model.Event
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class CharactersRepositoryImpl @Inject constructor(
    private val remoteDataSource:CharactersRemoteDataSource,
    private val dataBase:AppDatabase
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

    override fun getCachedCharacters(
        query: String,
        orderBy:String,
        pagingConfig: PagingConfig
    ): Flow<PagingData<Character>> {
        return Pager(
            config = pagingConfig,
            remoteMediator = CharactersRemoteMediator(
               query =  query, orderBy = orderBy, dataBase = dataBase, remoteDataSource = remoteDataSource
            )
        ){
            dataBase.characterDao().pagingSource()
        }.flow.map { pagingData ->
            pagingData.map {
                Character(
                    it.id,
                    it.name,
                    it.imageUrl
                )
            }
        }
    }
}