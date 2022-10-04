package com.example.marvelapp.framework.network.response

import com.google.gson.annotations.SerializedName

data class ThumbnailResponse(
    @SerializedName("path")
    val path:String, //"http://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784",
    @SerializedName("extension")
    val extension: String //"jpg"
)

fun ThumbnailResponse.getHttpsUrl() = "$path.$extension".replace("http","https")
