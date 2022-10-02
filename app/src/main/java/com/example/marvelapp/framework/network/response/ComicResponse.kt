package com.example.marvelapp.framework.network.response

import com.google.gson.annotations.SerializedName
import me.davidsonsilva.core.domain.model.Comic

data class ComicResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("thumbnail")
    val thumbnail: ThumbnailResponse
)

fun ComicResponse.toComicModel(): Comic {
    return Comic(
        id = this.id,
        imageUrl = "${this.thumbnail.path}.${this.thumbnail.extension}"
            .replace("http","https")
    )
}
