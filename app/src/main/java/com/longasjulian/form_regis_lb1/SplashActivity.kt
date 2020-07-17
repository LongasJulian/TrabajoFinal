package com.longasjulian.form_regis_lb1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.Window
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.longasjulian.form_regis_lb1.database.datosEmpleados
import java.util.*
import kotlin.concurrent.timerTask



class SplashActivity : AppCompatActivity() {
    val mAuth : FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_splash)

        val user = mAuth.currentUser
        if (user != null)
            user.email?.let { Buscar(it) }
        else{
            goToLoginMainActivity()
        }

    }

    override fun onStart() {
        super.onStart()

    }

    private fun goToMainActivityJefe() {
        var intent = Intent(this, MainActivityJefe::class.java)
        startActivity(intent)
        finish()
    }
    private fun goToMainActivityEmpleado() {
        var intent = Intent(this, MainActivityEmpleado::class.java)
        startActivity(intent)
        finish()
    }


    private fun goToLoginMainActivity(){
        val timer = Timer()
        val intent = Intent(this, LoginActivity2::class.java)
        timer.schedule(timerTask {
            startActivity(intent)
            finish()
        }, 2000
        )

    }

    private fun Buscar(email: String?) {
        val dataBase: FirebaseDatabase = FirebaseDatabase.getInstance()
        var myRef : DatabaseReference = dataBase.getReference("datostrabajo")
        myRef = dataBase.getReference("datostrabajo")
        var empleadoExiste = false
        val postListener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }
            override fun onDataChange(snapshot: DataSnapshot) {
                for (datasnapshot: DataSnapshot in snapshot.children) {
                    val trabajador = datasnapshot.getValue(datosEmpleados::class.java)

                    if (trabajador?.correo == email){
                        empleadoExiste = true
                        if(trabajador?.cargo=="Jef@")
                            goToMainActivityJefe()
                        else{
                            goToMainActivityEmpleado()
                        }
                    }
                }
            }

        }
        myRef.addListenerForSingleValueEvent(postListener)
    }
}