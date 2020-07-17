package com.longasjulian.form_regis_lb1

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.database.*
import com.longasjulian.form_regis_lb1.database.datosEmpleados
import kotlinx.android.synthetic.main.activity_login2.*


class LoginActivity2 : AppCompatActivity() {
    val mAuth : FirebaseAuth = FirebaseAuth.getInstance()
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


            val email    =     loginCorreo_TV.text.toString()
            val password=     loginPassword_TV.text.toString()


            if(email.isEmpty() || password.isEmpty()){
                Toast.makeText(this,"Por favor complete todos los campos", Toast.LENGTH_SHORT).show()
            }
            else{

                mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(
                        this
                    ) { task ->
                        if (task.isSuccessful) {
                            Buscar(email)
                        } else {
                            printError(task)
                        }
                    }

            }
        }
    }

    private fun Buscar(email: String) {
        val dataBase: FirebaseDatabase = FirebaseDatabase.getInstance()
        var myRef : DatabaseReference = dataBase.getReference("datostrabajo")
        myRef = dataBase.getReference("datostrabajo")
        var empleadoExiste = false
        val postListener = object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
            }
            override fun onDataChange(snapshot: DataSnapshot) {
                for (datasnapshot: DataSnapshot in snapshot.children) {
                    val trabajador = datasnapshot.getValue(datosEmpleados::class.java)

                    if (trabajador?.correo == email){
                        empleadoExiste = true
                        if(trabajador.cargo=="Jef@")
                            goToMainActivityJefe(trabajador.nombre)
                        else{
                            goToMainActivityEmpleado(trabajador.nombre)
                        }
                    }
                }
                if (!empleadoExiste)
                    Toast.makeText(this@LoginActivity2,"Empleado No existe", Toast.LENGTH_SHORT).show()

            }

        }
        myRef.addListenerForSingleValueEvent(postListener)
    }


    private fun goToMainActivityJefe(nombre: String) {
        var intent = Intent(this, MainActivityJefe::class.java)
        intent.putExtra("nombre",nombre )
        startActivity(intent)
        finish()
    }
    private fun goToMainActivityEmpleado(nombre: String) {
        var intent = Intent(this, MainActivityEmpleado::class.java)
        intent.putExtra("nombre",nombre )
        startActivity(intent)
        finish()
    }

    //Funcion para imprimir los errores al ingresar
    private fun printError(task: Task<AuthResult>) {
        val errorCode =
            (task.exception as FirebaseAuthException?)!!.errorCode

        when (errorCode) {
            "ERROR_WRONG_PASSWORD" ->
                Toast.makeText(this, "La contraseña es incorrecta", Toast.LENGTH_LONG).show();
            "ERROR_INVALID_EMAIL" ->
                Toast.makeText(
                    this,
                    "El correo está mal ingresado, revisalo de nuevo",
                    Toast.LENGTH_LONG
                ).show();
            "ERROR_USER_NOT_FOUND" ->
                Toast.makeText(
                    this,
                    " El Correo no está registrado o fue eliminado ",
                    Toast.LENGTH_LONG
                ).show();
            else -> {
                Toast.makeText(
                    this,
                    "User Authentication Failed: " + task.getException()?.message,
                    Toast.LENGTH_SHORT
                ).show();
            }
        }
    }
}