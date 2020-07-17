package com.longasjulian.form_regis_lb1

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.longasjulian.form_regis_lb1.database.datosEmpleados
import kotlinx.android.synthetic.main.activity_register.*
import java.text.SimpleDateFormat
import java.util.*


class RegisterActivity : AppCompatActivity() {
    val mAuth : FirebaseAuth = FirebaseAuth.getInstance()
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
            val email        =     Correo_ET.text.toString()
            val Telefono      =     Telefono_ET.text.toString()
            val numeroCuenta  =      Cuenta_ET.text.toString()
            val password    =     Contrasena_ET.text.toString()
            val NewContrasena =     Rcontrasena_ET.text.toString()

            //Radio Button
            val cargo = if(Jefe_RB.isChecked) "Jef@" else "Emplead@"

            //Lista
            var banco    = Banco_SP.selectedItem.toString()

            //Calendario
            var FechaNacimiento= FechaNacimiento_TV.getText().toString() //Toma el texto que hay en el Text view y lo guarda en FechaNacimiento

            if (Nombre.isEmpty() || email.isEmpty() ||Telefono.isEmpty() || password.isEmpty() || NewContrasena.isEmpty()
                || FechaNacimiento.equals("MM/dd/yyyy /") || numeroCuenta.isEmpty() )
                Toast.makeText(this,"Por favor complete todos los campos", Toast.LENGTH_SHORT).show()
            else
                if(password!=NewContrasena)
                    Toast.makeText(this,"Las contraseñas no coinciden, digítela de nuevo", Toast.LENGTH_SHORT).show()
                else {

                    mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(
                            this
                        ) { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(this," Registro Exitoso, su correo  es  $email", Toast.LENGTH_LONG).show()
                                crearDatabase(Nombre, Telefono, email, Telefono, FechaNacimiento, banco, numeroCuenta,cargo)
                                val intent = Intent(this, LoginActivity2::class.java)
                                onBackPressed()
                                startActivity(intent)
                            } else {
                                printError(task)
                            }

                        }
                }
        }
    }

    private fun printError(task: Task<AuthResult>) {
        val errorCode =
            (task.exception as FirebaseAuthException?)!!.errorCode

        when (errorCode) {
            "ERROR_WEAK_PASSWORD" ->
                Toast.makeText(
                    this,
                    "La Contraseña debe de ser de almenos 6 caracteres",
                    Toast.LENGTH_LONG
                ).show();
            "ERROR_INVALID_EMAIL" ->
                Toast.makeText(
                    this,
                    "El correo está mal ingresado, revisalo de nuevo",
                    Toast.LENGTH_LONG
                ).show();
            "ERROR_EMAIL_ALREADY_IN_USE" ->
                Toast.makeText(this, "El correo ingresado ya está registrado ", Toast.LENGTH_LONG)
                    .show();
            else -> {
                Toast.makeText(
                    this,
                    "User Authentication Failed: " + task.getException()?.message,
                    Toast.LENGTH_SHORT
                ).show();
            }
        }
    }

    private fun crearDatabase(
        nombre: String,
        telefono: String,
        correo: String,
        celular: String,
        fechanacimiento: String,
        banco: String,
        numerocuenta: String,
        cargo: String
    ) {
        val database : FirebaseDatabase = FirebaseDatabase.getInstance()
        val myRef : DatabaseReference = database.getReference("datostrabajo")
        val id = myRef.push().key
        val datosEmpleados = datosEmpleados(
            id,
            nombre,
            telefono,
            correo,
            celular,
            fechanacimiento,
            banco,
            numerocuenta,
            cargo
        )
        myRef.child(id!!).setValue(datosEmpleados)
    }
}