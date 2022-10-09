package com.example.marvelapp.framework.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.marvelapp.framework.db.AppDatabase
import com.example.marvelapp.framework.db.dao.CharacterDao
import com.example.marvelapp.framework.db.dao.RemoteKeyDao
import com.example.marvelapp.framework.db.entity.CharacterEntity
import com.example.marvelapp.framework.db.entity.RemoteKey
import me.davidsonsilva.core.data.repository.CharactersRemoteDataSource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class CharactersRemoteMediator @Inject constructor(
    private val query: String,
    private val dataBase:AppDatabase,
    private val remoteDataSource: CharactersRemoteDataSource
) : RemoteMediator<Int, CharacterEntity>() {

    private val characterDao:CharacterDao = dataBase.characterDao()
    private val remoteKeyDao:RemoteKeyDao = dataBase.remoteKeyDao()

    @Suppress("ReturnCount")
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterEntity>
    ): MediatorResult {
        return try {
            val offset = when(loadType){
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = dataBase.withTransaction {
                        remoteKeyDao.remoteKey()
                    }
                    if (remoteKey.nextOffset == null) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }
                    remoteKey.nextOffset
                }
            }

            val queries = hashMapOf(
                "offset" to offset.toString()
            )

            if (query.isNotEmpty()) {
                queries["nameStartsWith"] = query
            }

            val characterPaging = remoteDataSource.fetchCharacters(queries)
            val responseOffSet = characterPaging.offset
            val totalCharacters = characterPaging.total

            dataBase.withTransaction {
                if(loadType == LoadType.REFRESH) {
                    remoteKeyDao.clearAll()
                    characterDao.clearAl()
                }

                remoteKeyDao.insertOrReplace(
                    RemoteKey(nextOffset = responseOffSet + state.config.pageSize)
                )

                val charactersEntities = characterPaging.characters.map {
                    CharacterEntity(
                        id = it.id, name = it.name, imageUrl = it.imageUrl
                    )
                }

                characterDao.insertAll(charactersEntities)
            }

            MediatorResult.Success(endOfPaginationReached = responseOffSet >= totalCharacters)
        } catch (e:IOException){
            MediatorResult.Error(e)
        }catch (ex:HttpException){
            MediatorResult.Error(ex)
        }

    }
}