package com.longasjulian.form_regis_lb1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.longasjulian.form_regis_lb1.database.datosEmpleados
import kotlinx.android.synthetic.main.activity_mi_perfil.*

class MiPerfilActivity : AppCompatActivity() {
    val mAuth : FirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mi_perfil)
        val user = mAuth.currentUser
        buscarInDatabese(user?.email.toString())

    }

    private fun buscarInDatabese(email: String) {
        val dataBase: FirebaseDatabase = FirebaseDatabase.getInstance()
        var myRef : DatabaseReference = dataBase.getReference("datostrabajo")
        myRef = dataBase.getReference("datostrabajo")
        val postListener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }
            override fun onDataChange(snapshot: DataSnapshot) {
                for (datasnapshot: DataSnapshot in snapshot.children) {
                    val trabajador = datasnapshot.getValue(datosEmpleados::class.java)
                    if (trabajador?.correo == email){
                        NombreMiPerfil_TV.text = trabajador.nombre
                        CorreoMiPerifl_TV.text = trabajador.correo
                        FechaMiPerfil_TV.text = trabajador.fechanacimiento
                        CelularPerfil_TV.text = trabajador.celular
                        BancoMiPerfil_TV.text = trabajador.banco
                        CuentaMiPerfil_TV.text = trabajador.numerocuenta
                    }
                }
            }

        }
        myRef.addListenerForSingleValueEvent(postListener)
    }
}