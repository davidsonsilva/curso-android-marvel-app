package com.example.marvelapp.framework.network.response

import com.google.gson.annotations.SerializedName
import me.davidsonsilva.core.domain.model.Character


data class CharacterResponse(
    @SerializedName("id")
    val id: Int,//1011334,
    @SerializedName("name")
    val name: String, //"3-D Man",
    @SerializedName("thumbnail")
    val thumbnail: ThumbnailResponse
)

fun CharacterResponse.toCharacterModel():Character {
    return Character(
        id = this.id,
        name = this.name,
        imageUrl = this.thumbnail.getHttpsUrl()
    )
}