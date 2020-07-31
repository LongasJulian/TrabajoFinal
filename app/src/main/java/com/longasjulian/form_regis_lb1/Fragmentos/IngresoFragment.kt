package com.longasjulian.form_regis_lb1.Fragmentos

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.longasjulian.form_regis_lb1.R
import com.longasjulian.form_regis_lb1.database.IngresoEmpleados
import com.longasjulian.form_regis_lb1.database.IngresoSalidaEmpleados
import com.longasjulian.form_regis_lb1.database.datosEmpleados
import kotlinx.android.synthetic.main.fragment_ingreso.*
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

class IngresoFragment : Fragment() {
    val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    var nombreEmpleado = ""
    private var cal= Calendar.getInstance()
    private var cal1 = Calendar.getInstance()
    private lateinit var fecha: String
    private lateinit var horaingreso: String
    private lateinit var horasalida: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val user = mAuth.currentUser
        buscarInDatabese(user?.email.toString())
        return inflater.inflate(R.layout.fragment_ingreso, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        FotoIngreso_IV.setOnClickListener {
            dispatchTakePictureIntent()
        }
        var format = "dd/MM/yyyy"
        var simpleDateFormat = SimpleDateFormat(format, Locale.US)

        Ingresar_TB.setOnClickListener {
            val preguntaabiertaingreso = EncuestaAbierta_TL.text.toString()
            fecha = simpleDateFormat.format(cal.time).toString()

            format = "HH:mm:ss"
            simpleDateFormat = SimpleDateFormat(format, Locale.US)
            horaingreso = simpleDateFormat.format(cal.time).toString()

            Toast.makeText(requireContext(), "$fecha $horaingreso",Toast.LENGTH_SHORT).show()

            guardarIngreso(
                nombreEmpleado,
                fecha,
                horaingreso,
                preguntaabiertaingreso
            )

            EncuestaAbierta_TL.visibility = View.GONE
            Ingresar_TB.visibility = View.GONE

            EncuestaAbiertaSalida_TL.visibility = View.VISIBLE
            EncuestaAbiertaSalida_TL.setText("")
            Salida_TB.visibility = View.VISIBLE
        }

        Salida_TB.setOnClickListener {
            val preguntaabiertasalida = EncuestaAbiertaSalida_TL.text.toString()

            cal1 = Calendar.getInstance()
            var formathora = "HH:mm:ss"
            var simpleDateFormathora = SimpleDateFormat(formathora, Locale.US)
            horasalida = simpleDateFormathora.format(cal1.time).toString()
            Toast.makeText(requireContext(), "$fecha $horasalida",Toast.LENGTH_SHORT).show()

            guardarSalida(
                nombreEmpleado,
                fecha,
                horasalida,
                preguntaabiertasalida
            )

            EncuestaAbierta_TL.visibility = View.VISIBLE
            EncuestaAbierta_TL.setText("")
            Ingresar_TB.visibility = View.VISIBLE

            EncuestaAbiertaSalida_TL.visibility = View.GONE
            Salida_TB.visibility = View.GONE

        }
    }

    private fun guardarIngreso(
        nombre: String,
        fecha: String,
        horaingreso: String,
        preguntaabiertaingreso: String
    ) {
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val myRef: DatabaseReference = database.getReference("ingresoempleados")
        val id = myRef.push().key

        val mStorage = FirebaseStorage.getInstance()
        val photoRef = mStorage.reference.child(id!!)
        var urlPhoto = ""

        FotoIngreso_IV.isDrawingCacheEnabled = true
        FotoIngreso_IV.buildDrawingCache()
        val bitmap = (FotoIngreso_IV.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        val uploadTask = photoRef.putBytes(data)

        val urlTask = uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            photoRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                urlPhoto = task.result.toString()
                val ingreso = IngresoEmpleados(
                    id,
                    nombre,
                    preguntaabiertaingreso,
                    fecha,
                    horaingreso,
                    urlPhoto
                )
                myRef.child(id!!).setValue(ingreso)
            }
        }
    }

    private fun guardarSalida(
        nombreEmpleado: String,
        fecha: String,
        horasalida: String,
        preguntaabiertasalida: String
    ) {
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val myRef: DatabaseReference = database.getReference("ingresosalidaempleados")
        val id = myRef.push().key
        val salida = IngresoSalidaEmpleados(
            id,
            nombreEmpleado,
            fecha,
            horasalida,
            preguntaabiertasalida
        )
        myRef.child(id!!).setValue(salida)
    }



    private fun buscarInDatabese(email: String) {
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
                        nombreEmpleado = trabajador.nombre.toString()
                      //  NombreIngreso_TL.setText(nombreEmpleado)
                    }
                }
            }

        }
        myRef.addListenerForSingleValueEvent(postListener)
    }

    private fun dispatchTakePictureIntent() {
        Intent (MediaStore.ACTION_IMAGE_CAPTURE).also{ takePictureIntent ->
            takePictureIntent.resolveActivity(requireActivity().packageManager)?.also{
                startActivityForResult(takePictureIntent, 1234)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1234 && resultCode == Activity.RESULT_OK){
            val imageBitmap = data?.extras?.get("data") as Bitmap
            FotoIngreso_IV.setImageBitmap(imageBitmap)
        }
    }




}