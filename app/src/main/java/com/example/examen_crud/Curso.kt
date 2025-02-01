package com.example.examen_crud

data class Curso(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val duracion: Int,
    val ubicacion: String? // Ahora se almacena en un solo campo como "latitud, longitud"
)
