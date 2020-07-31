package com.longasjulian.form_regis_lb1.Fragmentos

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.database.*
import com.longasjulian.form_regis_lb1.R
import com.longasjulian.form_regis_lb1.database.IngresoEmpleados
import com.longasjulian.form_regis_lb1.database.IngresoSalidaEmpleados
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_empleados.*
import java.text.SimpleDateFormat
import java.util.*

class EmpleadosFragment : Fragment() {

    private lateinit var date: String //inicializarla despues
    private var cal= Calendar.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_empleados, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        TarjetaEmpleado_FL.visibility = View.GONE
        val dataSetListener =
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, month)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val format = "dd/MM/yyyy"
                val simpleDateFormat = SimpleDateFormat(format, Locale.US)
                date = simpleDateFormat.format(cal.time).toString()
                FechaEmpleadoInOut_TV.text=date
            }

        CalendarioEmpleado_BT.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                dataSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        BuscarEmpleado_BTT.setOnClickListener {
            val nombre = NombreEmpleado_TV.text.toString()
            val fecha = FechaEmpleadoInOut_TV.text.toString()
            cargarEmpleadoIngreso(nombre,fecha)

        }

    }

    private fun cargarEmpleadoIngreso(nombre: String, fecha: String) {
        val dataBase: FirebaseDatabase = FirebaseDatabase.getInstance()
        var myRef : DatabaseReference = dataBase.getReference("ingresoempleados")
        val postListener = object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for(datasnapshot: DataSnapshot in snapshot.children) {
                    val ingreso = datasnapshot.getValue(IngresoEmpleados::class.java)
                    if((ingreso?.nombre == nombre)&&(ingreso?.fechaingreso == fecha)) {
                            NombreOut_TV.text = ingreso.nombre
                            FechaOut_TV.text = ingreso.fechaingreso
                            HoraOutIngreso_TV.text = ingreso.horaingreso
                            NovedadesOutIngreso_TV.text = "${ingreso.encuestaabiertaingreso}"
                            Picasso.get().load(ingreso.foto).into(FotoIngresoOut_IV)
                            TarjetaEmpleado_FL.visibility = View.VISIBLE
                    }
                    else{
                        Toast.makeText(requireContext(),"El nombre del empleado ingresado no encontrado",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        myRef.addListenerForSingleValueEvent(postListener)
        CargarEmpleadoSalida(nombre,fecha)
    }

    private fun CargarEmpleadoSalida(nombre: String, fecha: String) {
        val dataBase: FirebaseDatabase = FirebaseDatabase.getInstance()
        var myRef : DatabaseReference = dataBase.getReference("ingresosalidaempleados")
        val postListener = object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for(datasnapshot: DataSnapshot in snapshot.children) {
                    val salida = datasnapshot.getValue(IngresoSalidaEmpleados::class.java)
                    if((salida?.nombre == nombre)&&(salida?.fechasalida == fecha)){
                            HoraOutSalida_TV.text = salida.horasalida
                            NovedadesOutSalida_TV.text = salida.encuestaabiertasalida
                    }
                    else{
                        HoraOutSalida_TV.text = "N.R"
                        NovedadesOutSalida_TV.text= "NR"
                    }
                }
            }
        }
        myRef.addListenerForSingleValueEvent(postListener)

    }
}