package com.longasjulian.form_regis_lb1

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.longasjulian.form_regis_lb1.database.Anuncios
import com.longasjulian.form_regis_lb1.database.datosEmpleados
import kotlinx.android.synthetic.main.activity_agregar_anuncios.*
import kotlinx.android.synthetic.main.anuncios_item.*
import java.io.ByteArrayOutputStream

class AgregarAnunciosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_anuncios)

        FotoAnuncios_TV.setOnClickListener {
            dispatchTakePictureIntent()
        }

        Publicar_BT.setOnClickListener {

            val asunto = TituloAnuncios_TL.text.toString()
            val mensaje = MensajeAnuncios_TL.text.toString()
            crearDatabase(asunto,mensaje)
            goToMainActivityJefe()

        }

    }

    private fun dispatchTakePictureIntent() {
        Intent (MediaStore.ACTION_IMAGE_CAPTURE).also{takePictureIntent ->
            takePictureIntent.resolveActivity(this.packageManager)?.also{
                startActivityForResult(takePictureIntent, 1234)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1234 && resultCode == Activity.RESULT_OK){
            val imageBitmap = data?.extras?.get("data") as Bitmap
            FotoAnuncios_TV.setImageBitmap(imageBitmap)
        }
    }

    private fun crearDatabase(
        asunto: String,
        mensaje: String
    ) {


        val database : FirebaseDatabase = FirebaseDatabase.getInstance()
        val myRef : DatabaseReference = database.getReference("anuncios")
        val id = myRef.push().key

        val mStorage = FirebaseStorage.getInstance()
        val photoRef = mStorage.reference.child(id!!)
        var urlPhoto = ""

        // Get the data from an ImageView as bytes
        FotoAnuncios_TV.isDrawingCacheEnabled = true
        FotoAnuncios_TV.buildDrawingCache()
        val bitmap = (FotoAnuncios_TV.drawable as BitmapDrawable).bitmap
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
                val anuncios = Anuncios(
                    id,
                    asunto,
                    mensaje,
                    urlPhoto
                )
                myRef.child(id).setValue(anuncios)
            } else {
                // Handle failures
                // ...
            }
        }
    }

    private fun goToMainActivityJefe() {
        onBackPressed()
        finish()
    }


}