package com.example.marvelapp.framework.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import me.davidsonsilva.core.data.repository.CharactersRemoteDataSource
import me.davidsonsilva.core.domain.model.Character

class CharactersPagingSource(
    private val remoteDataSource: CharactersRemoteDataSource,
    private val query: String
) : PagingSource<Int, Character>() {

    @Suppress("TooGenericExceptionCaught")
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        return try {
            val offSet = params.key ?: 0

            val queries = hashMapOf(
                "offset" to offSet.toString()
            )

            if (query.isNotEmpty()) {
                queries["nameStartsWith"] = query
            }

            val characterPaging = remoteDataSource.fetchCharacters(queries)

            val responseOffSet = characterPaging.offset
            val totalCharacters = characterPaging.total

            LoadResult.Page(
                data = characterPaging.characters,
                prevKey = null,
                nextKey = if (responseOffSet < totalCharacters) {
                    responseOffSet + LIMIT
                } else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(LIMIT) ?: anchorPage?.nextKey?.minus(LIMIT)
        }
    }

    companion object {
        private const val LIMIT = 20
    }

}



