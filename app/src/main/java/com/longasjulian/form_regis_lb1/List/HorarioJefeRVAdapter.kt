package com.longasjulian.form_regis_lb1.List

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.longasjulian.form_regis_lb1.R
import com.longasjulian.form_regis_lb1.database.Horarios
import kotlinx.android.synthetic.main.horarios_item.view.*

class HorarioJefeRVAdapter(
    var horariojefeList: MutableList<Horarios>
):RecyclerView.Adapter<HorarioJefeRVAdapter.HorarioJefeViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorarioJefeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.horarios_item, parent, false)
        return HorarioJefeViewHolder(itemView)
    }

    override fun getItemCount(): Int = horariojefeList.size


    override fun onBindViewHolder(holder: HorarioJefeViewHolder, position: Int) {
        val horarios = horariojefeList[position]
        holder.bindHorariosJefe(horarios)
    }

    class HorarioJefeViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        fun bindHorariosJefe(horariojefe: Horarios) {
            itemView.NombreHorario_TV.text = horariojefe.nombre
            itemView.HoraEntrada_TV.text = horariojefe.horaentrada
            itemView.HoraSalida_TV.text = horariojefe.horasalida
        }
    }


}