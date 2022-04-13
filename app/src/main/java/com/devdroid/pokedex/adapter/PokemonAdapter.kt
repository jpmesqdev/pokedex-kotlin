package com.devdroid.pokedex.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.devdroid.pokedex.R
import com.devdroid.pokedex.model.Pokemon
import com.devdroid.pokedex.model.PokemonInfo
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
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.pokemon_type_item, parent, false)

        return PokemonHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonHolder, position: Int) {

        val id = dataSet[position].pokemon.url
            .split("/")
            .dropLast(1)
            .last()
            .toInt()

        retrofit().create(PokeApiService::class.java)
            .getPokemonById(id)
            .enqueue(object : Callback<PokemonInfo> {
                override fun onResponse(call: Call<PokemonInfo>, response: Response<PokemonInfo>) {
                    if (response.isSuccessful) {
                        response.body()?.let { it ->
                            val typeArraySize = it.types.size
                            val type1 = it.types[0].label.name
                            val type2 = if (typeArraySize > 1) it.types[1].label.name else null

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

                            holder.pokemonLabel1.text = type1

                            applyAccordingToTypes(holder, typeArraySize, type1, type2)
                        }
                    }
                }

                override fun onFailure(call: Call<PokemonInfo>, t: Throwable) {
                    Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
                }
            }
        )
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    private fun getTypeColorByName(type: String): Int {
        return when(type) {
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
    }

    private fun getTypeColorGradient(type: String): Int {
        return when (type) {
            "dragon" -> R.drawable.gradient_dragon
            "flying" -> R.drawable.gradient_flying
            "ground" -> R.drawable.gradient_ground
            else -> {R.drawable.category_gradient_background}
        }
    }

    private fun changeLabelBackground(context: Context, type: String) {
        val unwrappedDrawable = AppCompatResources.getDrawable(context, R.drawable.label_background)
        val wrappedDrawable = unwrappedDrawable?.let { drawable -> DrawableCompat.wrap(drawable) }
        if (wrappedDrawable != null) {
            DrawableCompat.setTint(wrappedDrawable, context.resources.getColor(getTypeColorByName(type)))
        }
    }

    private fun applyAccordingToTypes(
        holder: PokemonHolder,
        typeArraySize: Int,
        type1: String,
        type2: String?
    ) {
        if (type1 == "dragon" || type1 == "flying" || type1 == "ground") {
            holder.pokemonLabel1
                .setBackgroundResource(getTypeColorGradient(type1))
            holder.pokemonLabel1.text = type1
            if (typeArraySize > 1) {
                holder.pokemonLabel2.text = type2
                if (type2 == "dragon" || type2 == "flying" || type2 == "ground") {
                    holder.pokemonLabel2
                        .setBackgroundResource(getTypeColorGradient(type2))
                } else {
                    if (type2 != null) {
                        changeLabelBackground(context, type2)
                        holder.pokemonLabel2
                            .setBackgroundResource(R.drawable.label_background)
                    }
                }
            } else {
                holder.pokemonLabel2.visibility = View.GONE
            }
        } else {
            holder.pokemonLabel1.text = type1
            changeLabelBackground(context, type1)
            holder.pokemonLabel1
                .setBackgroundResource(R.drawable.label_background)
            if (typeArraySize > 1) {
                holder.pokemonLabel2.text = type2
                if (type2 == "dragon" || type2 == "flying" || type2 == "ground") {
                    holder.pokemonLabel2
                        .setBackgroundResource(getTypeColorGradient(type2))
                } else {
                    if (type2 != null) {
                        changeLabelBackground(context, type2)
                        holder.pokemonLabel2
                            .setBackgroundResource(R.drawable.label_background)
                    }
                }
            } else {
                holder.pokemonLabel2.visibility = View.GONE
            }
        }
    }
}