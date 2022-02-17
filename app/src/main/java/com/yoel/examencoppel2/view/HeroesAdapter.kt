package com.yoel.examencoppel2.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.yoel.examencoppel2.R
import com.yoel.examencoppel2.model.HeroObject
import com.yoel.examencoppel2.utilities.GlobalClass

class HeroesAdapter(val context : Context,val arrayHeroes : ArrayList<HeroObject>,val listener : (HeroObject) -> Unit) : RecyclerView.Adapter<HeroesAdapter.ViewHolder>() {
    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val heroImage = itemView.findViewById<ImageView>(R.id.imageHero)!!
        val nameHero = itemView.findViewById<TextView>(R.id.nameHero)!!
        val heroComics = itemView.findViewById<TextView>(R.id.comics)!!
        val heroSeries = itemView.findViewById<TextView>(R.id.series)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.heroes_item_list,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val heroe = arrayHeroes[position]
        holder.nameHero.text = heroe.name
        holder.heroComics.text = heroe.comics.toString()
        holder.heroSeries.text = heroe.series.toString()
        Picasso.with(context).load("${heroe.thumbnail.path}.${heroe.thumbnail.extension}").into(holder.heroImage)
        holder.itemView.setOnClickListener {

            ViewCompat.setTransitionName(holder.heroImage, "item_image")
            listener(heroe)
        }
    }

    override fun getItemCount(): Int {
        return arrayHeroes.size
    }
}