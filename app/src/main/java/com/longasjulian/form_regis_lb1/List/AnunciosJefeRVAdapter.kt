package com.longasjulian.form_regis_lb1.List

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.longasjulian.form_regis_lb1.R
import com.longasjulian.form_regis_lb1.database.Anuncios
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.anuncios_item.view.*

class AnunciosJefeRVAdapter(
    var anunciosjefeList: MutableList<Anuncios>
):RecyclerView.Adapter<AnunciosJefeRVAdapter.AnunciosJefeViewHolder>() {


    override fun getItemCount(): Int = anunciosjefeList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnunciosJefeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.anuncios_item, parent, false)
        return AnunciosJefeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AnunciosJefeViewHolder, position: Int) {
        val anuncios = anunciosjefeList[position]
        holder.bindAnunciosJefe(anuncios)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    class AnunciosJefeViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        fun bindAnunciosJefe(anunciosjefe: Anuncios){
            itemView.Titulo_TV.text = anunciosjefe.asunto
            itemView.Mensaje_TV.text = anunciosjefe.mensaje
            itemView.FechaAnuncios_TV.text = anunciosjefe.fecha
            if(!anunciosjefe.foto.isNullOrEmpty())
                Picasso.get().load(anunciosjefe.foto).into(itemView.Foto_IV)
        }

    }



}