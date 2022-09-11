package com.example.marvelapp.framework

import androidx.paging.PagingSource
import com.example.marvelapp.framework.network.response.DataWrapperResponse
import me.davidsonsilva.core.data.repository.CharactersRemoteDataSource
import me.davidsonsilva.core.data.repository.CharactersRepository
import me.davidsonsilva.core.domain.model.Character
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(
    private val remoteDataSource:CharactersRemoteDataSource<DataWrapperResponse>
) : CharactersRepository {

    override fun getCharacters(query: String): PagingSource<Int, Character> {
        return CharactersPaging()
    }
}