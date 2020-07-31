package com.longasjulian.form_regis_lb1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.longasjulian.form_regis_lb1.List.EmpleadoJefeRVAdapter
import com.longasjulian.form_regis_lb1.database.datosEmpleados
import kotlinx.android.synthetic.main.activity_empleados_jefes.*

class EmpleadosJefesActivity : AppCompatActivity() {

    private val empleadosjefesList: MutableList<datosEmpleados> = mutableListOf()
    private lateinit var empleadojefeAdapter: EmpleadoJefeRVAdapter
    val mAuth : FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empleados_jefes)

        EmpleadosJefes_RV.layoutManager = LinearLayoutManager(
            this,
            RecyclerView.VERTICAL,
            false
        )

        EmpleadosJefes_RV.setHasFixedSize(true)

        val user = mAuth.currentUser
        val correo = user?.email.toString()
      //  Toast.makeText(this, correo, Toast.LENGTH_SHORT).show()
        BuscarCargo(correo)

        empleadojefeAdapter = EmpleadoJefeRVAdapter(empleadosjefesList as ArrayList<datosEmpleados>)
        EmpleadosJefes_RV.adapter = empleadojefeAdapter

    }

    private fun BuscarCargo(email: String) {
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
                        if(trabajador?.cargo == "Emplead@") {
                            cargarJefes()
                        }
                        else{
                            cargarEmpleados()
                        }
                    }
                }
            }

        }
        myRef.addListenerForSingleValueEvent(postListener)
    }

    private fun cargarJefes(){
        val dataBase: FirebaseDatabase = FirebaseDatabase.getInstance()
        var myRef : DatabaseReference = dataBase.getReference("datostrabajo")
        val postListener = object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                empleadosjefesList.clear()
                for(datasnapshot: DataSnapshot in snapshot.children){
                    val datostrabajo = datasnapshot.getValue(datosEmpleados::class.java)
                    if(datostrabajo?.cargo == "Jef@"){
                        empleadosjefesList.add(datostrabajo!!)
                    }
                }
                empleadojefeAdapter.notifyDataSetChanged()
            }

        }
        myRef.addListenerForSingleValueEvent(postListener)
    }


    private fun cargarEmpleados() {
        val dataBase: FirebaseDatabase = FirebaseDatabase.getInstance()
        var myRef : DatabaseReference = dataBase.getReference("datostrabajo")
        val postListener = object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                empleadosjefesList.clear()
                for(datasnapshot: DataSnapshot in snapshot.children){
                    val datostrabajo = datasnapshot.getValue(datosEmpleados::class.java)
                    if(datostrabajo?.cargo == "Emplead@"){
                        empleadosjefesList.add(datostrabajo!!)
                    }
                }
                empleadojefeAdapter.notifyDataSetChanged()
            }

        }
        myRef.addListenerForSingleValueEvent(postListener)
    }


}