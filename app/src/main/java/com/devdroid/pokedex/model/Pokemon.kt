package com.devdroid.pokedex.model

import com.google.gson.annotations.SerializedName

/**
 * Created by lovej on 05/04/2022.
 */

data class PokemonTypeList(@SerializedName("results") val pokemonTypeList: List<PokemonType>)
data class PokemonList(@SerializedName("pokemon") val pokemonTypeList: List<Pokemon>)

data class PokemonType(
    @SerializedName("name") var name: String = "",
    @SerializedName("url") var url: String = "",
)

data class Pokemon(
    @SerializedName("pokemon") var pokemon: PokemonItem = PokemonItem()
)

data class PokemonItem(
    @SerializedName("name") var name: String = "",
    @SerializedName("url") var url: String = ""
)

data class PokemonInfo(
    @SerializedName("id") var id: Int = 0,
    @SerializedName("name") var name: String = "",
    @SerializedName("sprites") var sprites: OtherSprites = OtherSprites()
    //@SerializedName("types") var types: PokemonLabel = PokemonLabel()
)

data class PokemonLabel(
    @SerializedName("type") var labels: List<Label> = listOf()
)

data class Label(
    @SerializedName("name") var name: String = ""
)

data class OtherSprites(
    @SerializedName("other") var sprite: ArtWork = ArtWork()
)

data class ArtWork(
    @SerializedName("official-artwork") var sprite: Sprite = Sprite()
)

data class Sprite(
    @SerializedName("front_default") var default: String = ""
)