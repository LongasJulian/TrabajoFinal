package com.longasjulian.form_regis_lb1.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface IngresoDAO {

    @Insert
    fun CrearIngreso(ingreso: Ingreso)

    @Query("SELECT * FROM tabla_ingreso WHERE nombre LIKE :nombre")
    fun BuscarIngreso(nombre: String) : Ingreso

}