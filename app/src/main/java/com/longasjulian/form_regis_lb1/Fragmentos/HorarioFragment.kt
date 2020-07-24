package com.longasjulian.form_regis_lb1.Fragmentos

import android.content.AbstractThreadedSyncAdapter
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.longasjulian.form_regis_lb1.List.HorarioJefeRVAdapter
import com.longasjulian.form_regis_lb1.List.HorarioRVAdapter
import com.longasjulian.form_regis_lb1.R
import com.longasjulian.form_regis_lb1.database.Horarios
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_horario.*
import kotlinx.android.synthetic.main.fragment_horario_jefe.*
import java.time.Month
import java.util.*


class HorarioFragment : Fragment() {
    private val horarioList: MutableList<Horarios> = mutableListOf()
    private lateinit var horariosAdapter: HorarioRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_horario, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        Horario_RV.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.VERTICAL,
            false
        )

        Horario_RV.setHasFixedSize(true)

        horariosAdapter = HorarioRVAdapter(horarioList as ArrayList<Horarios>)
        Horario_RV.adapter = horariosAdapter
        var fecha = ""
        CalendarHorario.setOnDateChangeListener{ calendarView: CalendarView, year, mes, dia ->
            Toast.makeText(requireContext(),"$dia/${mes+1}/$year",Toast.LENGTH_LONG).show()
            fecha ="$dia/${mes+1}/$year"
            cargarHorario(fecha)
        }

    }

    private fun cargarHorario(fecha: String) {
        val dataBase: FirebaseDatabase = FirebaseDatabase.getInstance()
        var myRef : DatabaseReference = dataBase.getReference("horario")
        val postListener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                horarioList.clear()
                for(datasnapshot: DataSnapshot in snapshot.children){
                    val horarios = datasnapshot.getValue(Horarios::class.java)
                    if(horarios?.fecha == fecha )
                        horarioList.add(horarios!!)
                }
                horariosAdapter.notifyDataSetChanged()
            }

        }
        myRef.addListenerForSingleValueEvent(postListener)

    }

}


