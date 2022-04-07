package com.devdroid.pokedex.model

import com.google.gson.annotations.SerializedName

/**
 * Created by lovej on 05/04/2022.
 */

data class PokemonTypeList(@SerializedName("results") val pokemonTypeList: List<PokemonType>)
data class PokemonByList(@SerializedName("pokemon") val pokemonTypeList: List<PokemonType>)

data class PokemonType(
    @SerializedName("name") var name: String = "",
    @SerializedName("url") var url: String = ""
)