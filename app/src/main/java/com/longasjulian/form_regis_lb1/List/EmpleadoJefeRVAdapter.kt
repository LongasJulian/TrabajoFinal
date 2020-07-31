package com.longasjulian.form_regis_lb1.List

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.longasjulian.form_regis_lb1.R
import com.longasjulian.form_regis_lb1.database.datosEmpleados
import kotlinx.android.synthetic.main.empleadojefe_item.view.*

class EmpleadoJefeRVAdapter(
    var empleadosjefeList: MutableList<datosEmpleados>
): RecyclerView.Adapter<EmpleadoJefeRVAdapter.EmpleadosJefeViewHolder>() {

    class EmpleadosJefeViewHolder(
        itemView: View
    ):RecyclerView.ViewHolder(itemView){

        fun bindEmpleadosJefe(empleadosjefes: datosEmpleados){
            itemView.NombreItemEJ_TV.text = empleadosjefes.nombre
            itemView.CorreoItemEJ_TV.text = empleadosjefes.correo
            itemView.FechaItemEJ_TV.text = empleadosjefes.fechanacimiento
            itemView.CelularItemEJ_TV.text = empleadosjefes.celular
            itemView.BancoItemEJ_TV.text = empleadosjefes.banco
            itemView.CuentaItemEJ_TV.text= empleadosjefes.numerocuenta
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmpleadosJefeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.empleadojefe_item, parent, false)
        return EmpleadosJefeViewHolder(itemView)
    }

    override fun getItemCount(): Int = empleadosjefeList.size

    override fun onBindViewHolder(holder: EmpleadosJefeViewHolder, position: Int) {
        val empleadosjefes = empleadosjefeList[position]
        holder.bindEmpleadosJefe(empleadosjefes)
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}