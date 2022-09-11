package com.example.marvelapp.framework.network.response

data class DataContainerResponse(
    val offSet:Int,
    val total:Int,
    val results: List<CharacterResponse>
)
