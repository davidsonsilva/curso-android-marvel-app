package me.davidsonsilva.core.data.repository

import androidx.paging.PagingSource
import me.davidsonsilva.core.domain.model.Character

interface CharactersRepository {

    fun getCharacters(query: String): PagingSource<Int, Character>
}