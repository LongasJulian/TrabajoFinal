package com.longasjulian.form_regis_lb1.Fragmentos

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.longasjulian.form_regis_lb1.List.AnunciosJefeRVAdapter
import com.longasjulian.form_regis_lb1.List.AnunciosRVAdapter
import com.longasjulian.form_regis_lb1.MainActivityJefe
import com.longasjulian.form_regis_lb1.R
import com.longasjulian.form_regis_lb1.database.Anuncios
import kotlinx.android.synthetic.main.fragment_anuncios.*
import kotlinx.android.synthetic.main.fragment_anuncios.Anuncios_RV
import kotlinx.android.synthetic.main.fragment_anuncios_jefe.*

class AnunciosJefeFragment : Fragment() {

    private val anunciosJefeList: MutableList<Anuncios> = mutableListOf()
    private lateinit var anunciosJefeAdapter : AnunciosJefeRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_anuncios_jefe, container, false)


    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cargarAnuncios()

        AnunciosJefe_RV.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.VERTICAL,
            false
        )

        AnunciosJefe_RV.setHasFixedSize(true)

        anunciosJefeAdapter = AnunciosJefeRVAdapter(anunciosJefeList as ArrayList<Anuncios>)
        AnunciosJefe_RV.adapter = anunciosJefeAdapter



    }

    private fun cargarAnuncios() {
        val dataBase: FirebaseDatabase = FirebaseDatabase.getInstance()
        var myRef : DatabaseReference = dataBase.getReference("anuncios")
        val postListener = object : ValueEventListener {

            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for (datasnapshot: DataSnapshot in snapshot.children) {
                    val anuncios = datasnapshot.getValue(Anuncios::class.java)
                    anunciosJefeList.add(anuncios!!)
                }
                anunciosJefeAdapter.notifyDataSetChanged()
            }

        }
        myRef.addListenerForSingleValueEvent(postListener)
    }

}