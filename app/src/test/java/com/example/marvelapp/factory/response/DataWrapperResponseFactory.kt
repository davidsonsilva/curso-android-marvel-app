package com.example.marvelapp.factory.response

import com.example.marvelapp.framework.network.response.CharacterResponse
import com.example.marvelapp.framework.network.response.DataContainerResponse
import com.example.marvelapp.framework.network.response.DataWrapperResponse
import com.example.marvelapp.framework.network.response.ThumbnailResponse
import me.davidsonsilva.core.domain.model.Character
import me.davidsonsilva.core.domain.model.CharacterPaging

class DataWrapperResponseFactory {

    fun create() = DataWrapperResponse<CharacterResponse>(
        copyright = "",
        data = DataContainerResponse<CharacterResponse>(
            offset = 0,
            total = 2,
            results = listOf(
                CharacterResponse(
                    id = 1011334,
                    name = "3-D Man",
                    thumbnail = ThumbnailResponse(
                        path = "http://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784",
                        extension = "jpg"
                    )

                ),
                CharacterResponse(
                    id = 1017100,
                    name = "A-Bomb (HAS)",
                    thumbnail = ThumbnailResponse(
                        path = "http://i.annihil.us/u/prod/marvel/i/mg/3/20/523215de5b16",
                        extension = "jpg"
                    )
                )
            )
        )
    )
}