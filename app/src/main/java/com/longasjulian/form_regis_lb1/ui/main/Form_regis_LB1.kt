package com.longasjulian.form_regis_lb1.ui.main

import android.app.Application
import androidx.room.Room
import com.longasjulian.form_regis_lb1.room.IngresoDataBase

class Form_regis_LB1 : Application() {

    companion object{

        lateinit var database: IngresoDataBase

    }
    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            this,
            IngresoDataBase::class.java,
            "ingresos_db"
        ).allowMainThreadQueries()
            .build()
    }


}