package com.devdroid.pokedex.service

import com.devdroid.pokedex.model.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by lovej on 05/04/2022.
 */
interface PokeApiService {
    @GET("type")
    fun listPokemonType(): Call<PokemonTypeList>

    @GET("type/{id}")
    fun listPokemonByType(@Path("id") id: Int): Call<PokemonList>

    @GET("pokemon/{id}")
    fun getPokemonById(@Path("id") id: Int): Call<PokemonInfo>
}

fun retrofit(): Retrofit =
    Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
