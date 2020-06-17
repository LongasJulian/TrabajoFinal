package com.longasjulian.form_regis_lb1

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.CorrectionInfo
import android.widget.DatePicker
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var date: String //inicializarla despues
    private var cal= Calendar.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dataSetListener =
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, month)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val format = "MM/dd/yyyy"
                val simpleDateFormat = SimpleDateFormat(format, Locale.US)
                date = simpleDateFormat.format(cal.time).toString()
                FechaNacimiento_TV.text=date
            }

        Calendario_BT.setOnClickListener{
            DatePickerDialog(
                this,
                dataSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        //Se declara la accion que se realiza a oprimir el boton
        Guardar_BT.setOnClickListener{
            //Text View
            val Nombre        =     Nombre_ET.text.toString()
            val Correo        =     Correo_ET.text.toString()
            val Telefono      =     Telefono_ET.text.toString()
            val Contrasena    =     Contrasena_ET.text.toString()
            val NewContrasena =     Rcontrasena_ET.text.toString()

            //Radio Button
            val Sexo = if(Hombre_RB.isChecked) "Masculino" else "Femenino"

            //Check Button
            var Cantar      =   ""
            var verPeliculas=   ""
            var Leer        =   ""
            var Acampar     =   ""
            if (Cantar_CB.isChecked)    Cantar      = "Cantar"
            if (Peliculas_CB.isChecked) verPeliculas= "Ver Películas"
            if (Leer_CB.isChecked)      Leer        = "Leer"
            if (Acampar_CB.isChecked)   Acampar     = "Acampar"


            //Calendario
            var FechaNacimiento= FechaNacimiento_TV.getText().toString() //Toma el texto que hay en el Text view y lo guarda en FechaNacimiento

            if (Nombre.isEmpty() || Correo.isEmpty() ||Telefono.isEmpty() || Contrasena.isEmpty() || NewContrasena.isEmpty() || FechaNacimiento.equals("MM/dd/yyyy /") )
                Resultado_TV.text = "Por favor complete todos los campos"
            else
                if(Contrasena!=NewContrasena)
                    Resultado_TV.text = "Las contraseñas no coinciden, digítela de nuevo"
                else
                    Resultado_TV.text = "Nombre => $Nombre\nCorreo Electrónico => $Correo\nTeléfono => $Telefono\nSexo => $Sexo\n" +
                                "Hobbies => $Cantar $verPeliculas $Leer $Acampar \n" +
                                "Fecha de Nacimiento= $FechaNacimiento "
        }
    }
}