package com.devdroid.pokedex

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devdroid.pokedex.adapter.PokemonAdapter
import com.devdroid.pokedex.model.*
import com.devdroid.pokedex.service.PokeApiService
import com.devdroid.pokedex.service.retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon)

        val typeUrl = intent.getStringExtra("url")
        val pokemonList = listOf<Pokemon>()
        val adapter = PokemonAdapter(pokemonList, this)
        val rv: RecyclerView = findViewById(R.id.pokemon_rv)

        rv.setHasFixedSize(true)
        rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv.adapter = adapter

        retrofit().create(PokeApiService::class.java)
            .listPokemonByType(2)
            .enqueue(object : Callback<PokemonList> {
                override fun onResponse(call: Call<PokemonList>, response: Response<PokemonList>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            adapter.dataSet = it.pokemonTypeList
                            adapter.notifyDataSetChanged()
                        }
                    }
                }

                override fun onFailure(call: Call<PokemonList>, t: Throwable) {
                    Toast.makeText(this@PokemonActivity, t.message, Toast.LENGTH_LONG).show()
                }

            })



    }
}