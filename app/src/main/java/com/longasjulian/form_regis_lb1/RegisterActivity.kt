package com.longasjulian.form_regis_lb1

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_register.*
import java.text.SimpleDateFormat
import java.util.*

class RegisterActivity : AppCompatActivity() {
    private lateinit var date: String //inicializarla despues
    private var cal= Calendar.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

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
            val numeroCuenta  =      Cuenta_ET.text.toString()
            val Contrasena    =     Contrasena_ET.text.toString()
            val NewContrasena =     Rcontrasena_ET.text.toString()

            //Radio Button
            val Sexo = if(Jefe_RB.isChecked) "Jef@" else "Emplead@"

            //Calendario
            var FechaNacimiento= FechaNacimiento_TV.getText().toString() //Toma el texto que hay en el Text view y lo guarda en FechaNacimiento

            if (Nombre.isEmpty() || Correo.isEmpty() ||Telefono.isEmpty() || Contrasena.isEmpty() || NewContrasena.isEmpty()
                || FechaNacimiento.equals("MM/dd/yyyy /") || numeroCuenta.isEmpty() )
                Toast.makeText(this,"Por favor complete todos los campos", Toast.LENGTH_SHORT).show()
            else
                if(Contrasena!=NewContrasena)
                    Toast.makeText(this,"Las contraseñas no coinciden, digítela de nuevo", Toast.LENGTH_SHORT).show()
                else {
                    val intent = Intent(this, LoginActivity2::class.java)

                    Toast.makeText(this,"Correo= $Correo", Toast.LENGTH_LONG).show()
                    intent.putExtra("Nombre", Nombre.toString())
                    intent.putExtra("Correo", Correo.toString())
                    intent.putExtra("Contrasena", Contrasena.toString())

                    onBackPressed()
                    startActivity(intent)


                }
        }
    }
}