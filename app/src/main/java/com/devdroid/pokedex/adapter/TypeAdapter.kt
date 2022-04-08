package com.devdroid.pokedex.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.devdroid.pokedex.MainActivity
import com.devdroid.pokedex.PokemonActivity
import com.devdroid.pokedex.R
import com.devdroid.pokedex.model.PokemonType
import com.devdroid.pokedex.model.PokemonTypeList

/**
 * Created by lovej on 03/04/2022.
 */
class TypeAdapter(
    var dataSet: List<PokemonType>,
    var context: Context,
    
) : RecyclerView.Adapter<TypeAdapter.TypeHolder>() {

    class TypeHolder(itemView: View, context: Context, dataSet: List<PokemonType>) : RecyclerView.ViewHolder(itemView) {
        val typeName: TextView = itemView.findViewById(R.id.type_txt_name)
        val typeImg: ImageView = itemView.findViewById(R.id.type_img)

        init {
            itemView.setOnClickListener {
                val intent = Intent(context, PokemonActivity::class.java).apply {
                    putExtra("URL", dataSet[adapterPosition].url)
                }
                context.startActivity(intent)
            }

        }

    }

    

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.type_cell, parent, false)
        return TypeHolder(view, context, dataSet)
    }

    override fun onBindViewHolder(holder: TypeHolder, position: Int) {
        holder.typeName.text = dataSet[position].name
        holder.typeImg.setImageResource(getTypeIconByName(dataSet[position].name))
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    private fun getTypeIconByName(name: String): Int {
        val icon = when(name) {
            "bug" -> R.drawable.type_bug
            "dark" -> R.drawable.type_dark
            "dragon" -> R.drawable.type_dragon
            "electric" -> R.drawable.type_electric
            "fairy" -> R.drawable.type_fairy
            "fighting" -> R.drawable.type_fighting
            "fire" -> R.drawable.type_fire
            "flying" -> R.drawable.type_flying
            "ghost" -> R.drawable.type_ghost
            "glass" -> R.drawable.type_glass
            "ground" -> R.drawable.type_ground
            "ice" -> R.drawable.type_ice
            "normal" -> R.drawable.type_normal
            "poison" -> R.drawable.type_poison
            "psychic" -> R.drawable.type_psychic
            "rock" -> R.drawable.type_rock
            "shadow" -> R.drawable.type_shadow
            "steel" -> R.drawable.type_steel
            else -> {R.drawable.type_water}
        }

        return icon
    }
}