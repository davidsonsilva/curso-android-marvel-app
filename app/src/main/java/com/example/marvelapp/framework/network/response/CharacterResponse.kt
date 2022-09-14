package com.example.marvelapp.framework.network.response

import me.davidsonsilva.core.domain.model.Character


data class CharacterResponse(
    val id: String,//1011334,
    val name: String, //"3-D Man",
    val thumbnail: ThumbnailResponse
)

fun CharacterResponse.toCharacterModel():Character {
    return Character(
        name = this.name,
        imageUrl = "${this.thumbnail.path}.${this.thumbnail.extension}"
            .replace("http","https")
    )
}