package com.longasjulian.form_regis_lb1.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tabla_ingreso")
class Ingreso (

    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "Nombre") val nombre: String,
    @ColumnInfo(name = "EncuestaIngreso1") val encuestaingreso1: String,
    @ColumnInfo(name = "EncuestaIngreso2") val encuestaingreso2: String,
    @ColumnInfo(name = "EncuestaIngreso3") val encuestaingreso3: String,
    @ColumnInfo(name = "PreguntaAbiertaIngreso") val preguntaabiertaingreso: String

){}