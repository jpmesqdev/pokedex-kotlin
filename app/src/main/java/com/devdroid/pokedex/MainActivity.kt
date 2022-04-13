package com.devdroid.pokedex

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devdroid.pokedex.adapter.TypeAdapter
import com.devdroid.pokedex.model.PokemonType
import com.devdroid.pokedex.model.PokemonTypeList
import com.devdroid.pokedex.service.PokeApiService
import com.devdroid.pokedex.service.retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rv: RecyclerView = findViewById(R.id.rv_type)
        val pokemonTypeList = listOf<PokemonType>()
        val adapter = TypeAdapter(pokemonTypeList, this)

        rv.layoutManager = GridLayoutManager(this, 2)
        rv.adapter = adapter

        retrofit().create(PokeApiService::class.java)
            .listPokemonType()
            .enqueue(object : Callback<PokemonTypeList> {
                override fun onResponse(
                    call: Call<PokemonTypeList>,
                    response: Response<PokemonTypeList>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            val pokemonList = it.pokemonTypeList.filter {
                                it.name != "shadow" && it.name != "unknown"
                            }
                            adapter.dataSet = pokemonList
                            adapter.notifyDataSetChanged()
                        }
                    }
                }

                override fun onFailure(call: Call<PokemonTypeList>, t: Throwable) {
                    Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_LONG).show()
                }

            }
        )
    }
}




