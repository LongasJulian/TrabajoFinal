package com.longasjulian.form_regis_lb1.Fragmentos

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.longasjulian.form_regis_lb1.AgregarHorarioActivity
import com.longasjulian.form_regis_lb1.List.HorarioJefeRVAdapter
import com.longasjulian.form_regis_lb1.R
import com.longasjulian.form_regis_lb1.database.Horarios
import kotlinx.android.synthetic.main.activity_agregar_horario.*
import kotlinx.android.synthetic.main.fragment_horario_jefe.*


class HorarioJefeFragment : Fragment() {
    private val horariojefeList: MutableList<Horarios> = mutableListOf()
    private lateinit var horariosJefeAdapter: HorarioJefeRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_horario_jefe, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        HorarioJefe_RV.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.VERTICAL,
            false
        )

        HorarioJefe_RV.setHasFixedSize(true)

        horariosJefeAdapter = HorarioJefeRVAdapter(horariojefeList as ArrayList<Horarios>)
        HorarioJefe_RV.adapter = horariosJefeAdapter

        AgregarHorarios_BT.setOnClickListener{
            var intent = Intent(activity, AgregarHorarioActivity::class.java)
            startActivity(intent)
        }
        var fecha = ""
        CalendarJefeHorario.setOnDateChangeListener{ calendarView: CalendarView, year, mes, dia ->
            Toast.makeText(requireContext(),"$year/${mes+1}/$dia", Toast.LENGTH_SHORT).show()
            fecha ="$dia/${mes+1}/$year"
            cargarHorario(fecha)

        }

    }

    private fun cargarHorario(fecha: String) {
        val dataBase: FirebaseDatabase = FirebaseDatabase.getInstance()
        var myRef : DatabaseReference = dataBase.getReference("horario")
        val postListener = object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                horariojefeList.clear()
                for(datasnapshot: DataSnapshot in snapshot.children){
                    val horarios = datasnapshot.getValue(Horarios::class.java)
                    if(horarios?.fecha == fecha )
                        horariojefeList.add(horarios!!)
                }
                horariosJefeAdapter.notifyDataSetChanged()
            }

        }
        myRef.addListenerForSingleValueEvent(postListener)

    }


}