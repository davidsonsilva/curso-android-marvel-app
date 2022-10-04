package com.example.testing.model

import me.davidsonsilva.core.domain.model.Character

class CharacterFactory {
    fun create(hero:Hero) = when (hero){
        Hero.ThreeDMan -> Character(
            id = 1011334,
            name = "3-D Man",
            imageUrl = "https://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784.jpg"
        )
        Hero.ABomb -> Character(
            id = 1017100,
            name = "A-Bomb (HAS)",
            imageUrl = "https://i.annihil.us/u/prod/marvel/i/mg/3/20/523215de5b16.jpg"
        )
    }

    sealed class Hero{
        object ThreeDMan :Hero()
        object ABomb :Hero()
    }
}