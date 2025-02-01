package com.example.examen_crud

data class Curso(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val duracion: Int,
    val latitud: Double?,   // Nueva propiedad
    val longitud: Double?   // Nueva propiedad
)
