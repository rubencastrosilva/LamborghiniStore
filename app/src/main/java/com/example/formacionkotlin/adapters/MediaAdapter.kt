package com.example.formacionkotlin.adapters

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.formacionkotlin.CAR_DETAIL_KEY
import com.example.formacionkotlin.CarDetailActivity
import com.example.formacionkotlin.R
import com.example.formacionkotlin.models.CarItem
import com.squareup.picasso.Picasso

/**
 * Este adaptador gestionará la lógica del listado a mostrar:
 *
 * 1- getItemCount() necesitará saber la cantidad de elementos que tiene la lista que queremos pintar
 * 2- onCreateViewHolder() necesitará saber que layout vamos a utilizar para pintar CADA elemento de "items"
 *    dicho método devuelve un ViewHolder, eso hará que salte el siguiente método onBindViewHolder
 * 3- onBindViewHolder este viewHolder manejará la vista a pintar, es decir, holder.itemView sería el layout principal de "view_media_item"
 *
 */

class MediaAdapter(val context: Context, var items: List<CarItem>) : RecyclerView.Adapter<MediaAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.view_media_item, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
        /*
         * Añadimos un listener "onClick" que saltará cuando pulsemos en la vista.
         * Recordamos que kotlin tiene una ventaja muy grande, las Lambdas, cuando hacemos un setOnClickListener { ... }
         * lo que hay dentro de los braquets es una lambda que define el bloque al que accederá cuando hagamos click, es decir,
         * la acción que queremos realizar al hacer click la definimos en el interior del bloque (la función lambda)
         */
        holder.itemView.setOnClickListener { startCarDetailActity(items[position]) }

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        /*
         * Declaramos las vistas que vayamos a manejar por cada View Holder (texto, imagen, spinners, botones...)
         */
        private val carTitle = view.findViewById(R.id.tv_car_title) as TextView
        private val carImageUrl = view.findViewById(R.id.iv_car_image) as ImageView

        /**
         * Este método es llamado directamente cada vez que pintamos un elemento de la lista, para poder pintar su contenido
         * Recordamos de item, es cada elemento de carItemResponse, que es una lista de "CarItem"
         */
        fun bind(item: CarItem) {
            carTitle.text = item.carModelName
            // Utilizamos la librería de picaso para pintar una imagen en un ImageView, en este caso, dada una url de una imagen almacenada en una web
            Picasso.with(carImageUrl.context).load(item.carImageUrl).into(carImageUrl)
        }
    }

    /**
     *
     */
    fun startCarDetailActity(item: CarItem) {

        val intent = Intent(context, CarDetailActivity::class.java)
        intent.putExtra(CAR_DETAIL_KEY, item)
        context.startActivity(intent)
    }
}

