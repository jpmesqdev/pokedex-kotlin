package com.devdroid.pokedex.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.res.ResourcesCompat.getColor
import androidx.core.graphics.drawable.DrawableCompat
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
import java.util.*

/**
 * Created by lovej on 10/04/2022.
 */
class PokemonAdapter(
    var dataSet: List<Pokemon>,
    val context: Context,
) : RecyclerView.Adapter<PokemonAdapter.PokemonHolder>() {
    class PokemonHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pokemonImg: ImageView = itemView.findViewById(R.id.pokemon_type_image)
        val pokemonNumber: TextView = itemView.findViewById(R.id.pokemon_type_number)
        val pokemonName: TextView = itemView.findViewById(R.id.pokemon_type_name)
        val pokemonLabel1: TextView = itemView.findViewById(R.id.pokemon_type_label_1)
        val pokemonLabel2: TextView = itemView.findViewById(R.id.pokemon_type_label_2)
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
                        response.body()?.let { it ->
                            val type = it.types[0].label.name

                            holder.pokemonNumber.text = String.format("Nº %1\$s", it.id.toString()
                                .padStart(3, '0'))

                            holder.pokemonName.text = it.name.replaceFirstChar {
                                if (it.isLowerCase()) it.titlecase(
                                    Locale.getDefault()
                                ) else it.toString()
                            }

                            Glide.with(context)
                                .load(it.sprites.other.official_artwork.front_default)
                                .apply(RequestOptions().override(200, 200))
                                .into(holder.pokemonImg)

                            holder.pokemonLabel1.text = type

                            if (type == "dragon" || type == "flying" || type == "ground") {
                                holder.pokemonLabel1.setBackgroundResource(getTypeColorGradient(type))
                                holder.pokemonLabel1.text = type
                                if (it.types.size > 1) {
                                    holder.pokemonLabel2.text = it.types[1].label.name
                                    holder.pokemonLabel2.setBackgroundResource(getTypeColorGradient(it.types[1].label.name))
                                } else {
                                    holder.pokemonLabel2.visibility = View.GONE
                                }

                            } else {
                                changeLabelBackground(context, type)
                                holder.pokemonLabel1.setBackgroundResource(R.drawable.label_background)

                                if (it.types.size > 1) {
                                    val type = it.types[1].label.name

                                    holder.pokemonLabel2.text = type

                                    changeLabelBackground(context, type)
                                    holder.pokemonLabel2.setBackgroundResource(R.drawable.label_background)
                                } else {
                                    holder.pokemonLabel2.visibility = View.GONE
                                }
                            }


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

    fun getTypeColorByName(type: String): Int {
        val color = when(type) {
            "bug" -> R.color.bug
            "dark" -> R.color.dark
            "electric" -> R.color.electric
            "fairy" -> R.color.fairy
            "fighting" -> R.color.fighting
            "fire" -> R.color.fire
            "water" -> R.color.water
            "ghost" -> R.color.ghost
            "grass" -> R.color.grass
            "ice" -> R.color.ice
            "normal" -> R.color.normal
            "poison" -> R.color.poison
            "psychic" -> R.color.psychic
            "rock" -> R.color.rock
            "shadow" -> R.color.black
            "steel" -> R.color.steel
            else -> {R.color.white}
        }

        return color
    }

    fun getTypeColorGradient(type: String): Int {
        val color = when (type) {
            "dragon" -> R.drawable.gradient_dragon
            "flying" -> R.drawable.gradient_flying
            "ground" -> R.drawable.gradient_ground
            else -> {R.drawable.category_gradient_background}
        }
        return color
    }

    fun changeLabelBackground(context: Context, type: String) {
        val unwrappedDrawable = AppCompatResources.getDrawable(context, R.drawable.label_background)
        val wrappedDrawable = unwrappedDrawable?.let { drawable -> DrawableCompat.wrap(drawable) }
        if (wrappedDrawable != null) {
            DrawableCompat.setTint(wrappedDrawable, context.resources.getColor(getTypeColorByName(type)))
        }
    }
}