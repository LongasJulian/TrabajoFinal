package com.longasjulian.form_regis_lb1.Fragmentos

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.longasjulian.form_regis_lb1.R
import com.longasjulian.form_regis_lb1.database.Anuncios
import com.longasjulian.form_regis_lb1.database.IngresoSalidaEmpleados
import com.longasjulian.form_regis_lb1.database.datosEmpleados
import kotlinx.android.synthetic.main.activity_agregar_anuncios.*
import kotlinx.android.synthetic.main.fragment_ingreso.*
import java.io.ByteArrayOutputStream

class IngresoFragment : Fragment() {

    var nombreEmpleado = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ingreso, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        FotoIngreso_IV.setOnClickListener {
            dispatchTakePictureIntent()
        }

        Salida_TB.setOnClickListener {
            val nombre = NombreIngreso_TL.text.toString()
            var encuestaingreso1 = ""
            var encuestaingreso2 = ""
            var encuestaingreso3 = ""
            var encuestasalida1 = ""
            var encuestasalida2 = ""
            var encuestasalida3 = ""
            if (EncuestaIngreso1_CB.isChecked) {
                encuestaingreso1 = "No encontré mi lugar de trabajo limpio y organizado"
            }
            if (EncuestaIngreso2_CB.isChecked) {
                 encuestaingreso2 = "Había muchos clientes a la hora de ingreso"
            }
            if (EncuestaIngreso3_CB.isChecked) {
                 encuestaingreso3 = "No se habían realizado aun las tareas diarias de ingreso"
            }
            val preguntaabiertaingreso = EncuestaAbierta_TL.text.toString()

            if (EncuestaSalida1_CB.isChecked) {
                encuestasalida1 = "No se lograron realizar todas las tareas diarias"
            }
            if (EncuestaSalida2_CB.isChecked) {
                encuestasalida2 = "A la hora de salir había poca clientela"
            }
            if (EncuestaSalida3_CB.isChecked) {
                encuestasalida3 = "Se realizaron todas las actividades de cierre"
            }
            val preguntaabiertasalida = EncuestaAbierta_TL.text.toString()

            //buscarInDatabese(nombre)
            guardarInDatabe(nombre,encuestaingreso1,encuestaingreso2,encuestaingreso3,preguntaabiertaingreso,
                encuestasalida1,encuestasalida2,encuestasalida3,preguntaabiertasalida)
            NombreIngreso_TL.setText("")
            EncuestaAbiertaSalida_TL.setText("")
            EncuestaAbierta_TL.setText("")
           // FotoIngreso_IV.setImageBitmap(R.mipmap.nombre as Bitmap)


        }

    }

    private fun guardarInDatabe(
        nombreEmpleado: String,
        encuestaingreso1: String,
        encuestaingreso2: String,
        encuestaingreso3: String,
        preguntaabiertaingreso: String,
        encuestasalida1: String,
        encuestasalida2: String,
        encuestasalida3: String,
        preguntaabiertasalida: String
    ) {
        val database : FirebaseDatabase = FirebaseDatabase.getInstance()
        val myRef : DatabaseReference = database.getReference("ingresosalidaempleados")
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
                val ingresosalida = IngresoSalidaEmpleados(
                    id,
                    nombreEmpleado,
                    encuestaingreso1,
                    encuestaingreso2,
                    encuestaingreso3,
                    preguntaabiertaingreso,
                    encuestasalida1,
                    encuestasalida2,
                    encuestasalida3,
                    preguntaabiertasalida,
                    urlPhoto
                )
                myRef.child(id!!).setValue(ingresosalida)
            }
        }

    }

    private fun buscarInDatabese(nombre: String) {
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

                    if (trabajador?.nombre == nombre){
                        empleadoExiste = true
                        nombreEmpleado = nombre

                    }
                }
                if (!empleadoExiste)
                    Toast.makeText(requireContext(),"Empleado no registrado", Toast.LENGTH_SHORT).show()
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