package com.longasjulian.form_regis_lb1

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login2.*
import kotlinx.android.synthetic.main.activity_register.*

class LoginActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_login2)

        var datosRecibidos = intent.extras

        Registrarse_TV.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        Ingreso_BT.setOnClickListener{


            val Correo    =     loginCorreo_TV.text.toString()
            val contrasena=     loginPassword_TV.text.toString()

           // val Nombre_IN           = datosRecibidos?.getString("nombre")
            val Correo_IN           = datosRecibidos?.getString("Correo")
            val Contrasena_IN       = datosRecibidos?.getString("Contrasena")

            //val nombre = Nombre_IN

            if(Correo.isEmpty() || contrasena.isEmpty()){
                Toast.makeText(this,"Por favor complete todos los campos", Toast.LENGTH_SHORT).show()
            }
            else{
                var datosRecibidos = intent.extras
                if( (Correo == Correo_IN)&&(contrasena == Contrasena_IN) ){
                    val intent = Intent(this, MainActivity3::class.java)
                    intent.putExtra("nombre", (datosRecibidos?.getString("Nombre")).toString())
                    intent.putExtra("contrasena", contrasena.toString())
                    intent.putExtra("correo", Correo.toString())
                    startActivity(intent)
                    finish()
                }
                else if ((Correo != Correo_IN)){
                    Toast.makeText(this,"Correo NO registrado", Toast.LENGTH_SHORT).show()
                }
                else if ( (Correo == Correo_IN)&&(contrasena != Contrasena_IN) ){
                    Toast.makeText(this,"Contraseña incorrecta", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}