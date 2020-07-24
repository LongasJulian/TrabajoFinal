package com.longasjulian.form_regis_lb1.Fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.longasjulian.form_regis_lb1.List.AnunciosRVAdapter
import com.longasjulian.form_regis_lb1.R
import com.longasjulian.form_regis_lb1.database.Anuncios
import com.longasjulian.form_regis_lb1.database.datosEmpleados
import kotlinx.android.synthetic.main.fragment_anuncios.*

class AnunciosFragment : Fragment() {

    private val anunciosList: MutableList<Anuncios> = mutableListOf()
    private lateinit var anunciosAdapter : AnunciosRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_anuncios, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Anuncios_RV.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.VERTICAL,
            false
        )

        Anuncios_RV.setHasFixedSize(true)

        cargarAnuncios()


         anunciosAdapter = AnunciosRVAdapter(anunciosList as ArrayList<Anuncios>)
        Anuncios_RV.adapter = anunciosAdapter
    }

    private fun cargarAnuncios() {
        val dataBase: FirebaseDatabase = FirebaseDatabase.getInstance()
        var myRef : DatabaseReference = dataBase.getReference("anuncios")

        val postListener = object : ValueEventListener {

            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                anunciosList.clear()
                for (datasnapshot: DataSnapshot in snapshot.children) {
                    val anuncios = datasnapshot.getValue(Anuncios::class.java)
                    anunciosList.add(anuncios!!)
                }
                anunciosList.reverse()
                anunciosAdapter.notifyDataSetChanged()
            }

        }
        myRef.addListenerForSingleValueEvent(postListener)
    }


}