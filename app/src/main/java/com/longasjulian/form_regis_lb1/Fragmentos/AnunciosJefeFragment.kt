package com.longasjulian.form_regis_lb1.Fragmentos

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.longasjulian.form_regis_lb1.AgregarAnunciosActivity
import com.longasjulian.form_regis_lb1.List.AnunciosJefeRVAdapter
import com.longasjulian.form_regis_lb1.R
import com.longasjulian.form_regis_lb1.database.Anuncios
import kotlinx.android.synthetic.main.fragment_anuncios_jefe.*
import kotlin.collections.ArrayList

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

        AgregarAnuncios_BT.setOnClickListener{
            var intent = Intent(activity,AgregarAnunciosActivity::class.java)
            startActivity(intent)
        }


    }


    private fun cargarAnuncios() {
        val dataBase: FirebaseDatabase = FirebaseDatabase.getInstance()
        var myRef : DatabaseReference = dataBase.getReference("anuncios")
        val postListener = object : ValueEventListener {

            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                anunciosJefeList.clear()
                for (datasnapshot: DataSnapshot in snapshot.children) {
                    val anuncios = datasnapshot.getValue(Anuncios::class.java)
                    anunciosJefeList.add(anuncios!!)
                }
                anunciosJefeList.reverse()
                anunciosJefeAdapter.notifyDataSetChanged()
            }

        }
        myRef.addListenerForSingleValueEvent(postListener)
    }

}