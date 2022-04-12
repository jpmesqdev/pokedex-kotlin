package com.devdroid.pokedex.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import com.devdroid.pokedex.PokemonActivity
import com.devdroid.pokedex.R
import com.devdroid.pokedex.model.Pokemon
import com.devdroid.pokedex.model.PokemonInfo
import com.devdroid.pokedex.model.PokemonType
import com.devdroid.pokedex.service.PokeApiService
import com.devdroid.pokedex.service.retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by lovej on 10/04/2022.
 */
class PokemonAdapter(
    var dataSet: List<Pokemon>,
    val context: Context,
) : RecyclerView.Adapter<PokemonAdapter.PokemonHolder>() {
    class PokemonHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var pokemonImg: ImageView = itemView.findViewById(R.id.pokemon_type_image)
        var pokemonNumber: TextView = itemView.findViewById(R.id.pokemon_type_number)
        var pokemonName: TextView = itemView.findViewById(R.id.pokemon_type_name)
        var pokemonLabel1: TextView = itemView.findViewById(R.id.pokemon_type_label_1)
        var pokemonLabel2: TextView = itemView.findViewById(R.id.pokemon_type_label_2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.pokemon_type_item, parent, false)
        return PokemonHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonHolder, position: Int) {
        val id = dataSet[position].pokemon.url.split("/").dropLast(1).last().toInt()

        retrofit().create(PokeApiService::class.java)
            .getPokemonById(id)
            .enqueue(object : Callback<PokemonInfo> {
                override fun onResponse(call: Call<PokemonInfo>, response: Response<PokemonInfo>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            holder.pokemonNumber.text = String.format("Nº %1\$s", it.id.toString()
                                .padStart(3, '0'))
                            holder.pokemonName.text = it.name
                            Glide.with(context)
                                .load(it.sprites.sprite.sprite.default)
                                .apply(RequestOptions().override(300, 300))
                                .into(holder.pokemonImg)
                        }
                    }
                }

                override fun onFailure(call: Call<PokemonInfo>, t: Throwable) {
                    Log.i("error", t.message.toString())
                }

            })


    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}