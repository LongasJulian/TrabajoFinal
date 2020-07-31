package com.longasjulian.form_regis_lb1.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Ingreso::class), version = 1)
abstract class IngresoDataBase : RoomDatabase() {

    abstract fun IngresoDAO() : IngresoDAO

}