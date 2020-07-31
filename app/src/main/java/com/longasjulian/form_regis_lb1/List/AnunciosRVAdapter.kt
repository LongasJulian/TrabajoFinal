package com.longasjulian.form_regis_lb1.List

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.longasjulian.form_regis_lb1.R
import com.longasjulian.form_regis_lb1.database.Anuncios
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.anuncios_item.view.*

class AnunciosRVAdapter(
    var anunciosList: MutableList<Anuncios>
): RecyclerView.Adapter<AnunciosRVAdapter.AnunciosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnunciosViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.anuncios_item,parent, false)
        return AnunciosViewHolder(itemView)
    }

    override fun getItemCount(): Int = anunciosList.size


    override fun onBindViewHolder(holder: AnunciosViewHolder, position: Int) {
        val anuncios = anunciosList[position]
        holder.bindAnuncios(anuncios)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }


    class AnunciosViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView){

        fun bindAnuncios(anuncios: Anuncios){
            itemView.Titulo_TV.text = anuncios.asunto
            itemView.Mensaje_TV.text = anuncios.mensaje
            itemView.FechaAnuncios_TV.text = anuncios.fecha
            if(!anuncios.foto.isNullOrEmpty())
                Picasso.get().load(anuncios.foto).into(itemView.Foto_IV)
        }

    }



}