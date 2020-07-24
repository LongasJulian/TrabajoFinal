package com.longasjulian.form_regis_lb1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CalendarView
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.longasjulian.form_regis_lb1.database.Horarios
import kotlinx.android.synthetic.main.activity_agregar_horario.*
import kotlinx.android.synthetic.main.fragment_horario.*

class AgregarHorarioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_horario)
        var fecha = ""
        val year = 0
        val mes = 0
        val dia = 0
        CalendarAgregarHorario.setOnDateChangeListener{ calendarView: CalendarView, year, mes, dia ->
            Toast.makeText(this,"$year/${mes+1}/$dia", Toast.LENGTH_SHORT).show()
            fecha ="$dia/${mes+1}/$year"
        }

        PublicarAgregarHorario_BT.setOnClickListener {
            var nombre = NombreIngresarHorario_TL.text.toString()
            var horasalida = inHoraEntrada_TL.text.toString()
            var horaentrada = inHoraSalida_TL.text.toString()

            GuardarEnFirebase(nombre,horaentrada,horasalida,fecha)
            onBackPressed()
            finish()

        }


    }

    private fun GuardarEnFirebase(
        nombre: String,
        horaentrada: String,
        horasalida: String,
        fecha: String
    ) {
        val database : FirebaseDatabase = FirebaseDatabase.getInstance()
        val myRef : DatabaseReference = database.getReference("horario")
        val id= myRef.push().key
        val horario = Horarios(
            id, nombre, horasalida, fecha, horaentrada
        )
        myRef.child(id!!).setValue(horario)
    }


}