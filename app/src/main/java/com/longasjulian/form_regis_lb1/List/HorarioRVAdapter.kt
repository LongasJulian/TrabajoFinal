package com.longasjulian.form_regis_lb1.List

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.longasjulian.form_regis_lb1.R
import com.longasjulian.form_regis_lb1.database.Anuncios
import com.longasjulian.form_regis_lb1.database.Horarios
import kotlinx.android.synthetic.main.horarios_item.view.*

class HorarioRVAdapter (
    var horarioList: MutableList<Horarios>
): RecyclerView.Adapter<HorarioRVAdapter.HorarioViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HorarioRVAdapter.HorarioViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.horarios_item, parent, false)
        return HorarioRVAdapter.HorarioViewHolder(itemView)
    }

    override fun getItemCount(): Int = horarioList.size

    override fun onBindViewHolder(holder: HorarioRVAdapter.HorarioViewHolder, position: Int) {
        val horarios = horarioList[position]
        holder.bindHorarios(horarios)
    }

    class HorarioViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        fun bindHorarios(horario: Horarios) {
            itemView.NombreHorario_TV.text = horario.nombre
            itemView.HoraEntrada_TV.text = horario.horaentrada
            itemView.HoraSalida_TV.text = horario.horasalida
        }
    }
}