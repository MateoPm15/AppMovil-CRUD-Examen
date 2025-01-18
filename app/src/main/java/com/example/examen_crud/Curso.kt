package com.example.examen_crud

class Curso (
    val id: Int,
    var nombre: String,
    var descripcion: String,
    var duracion: Int,
    var estudiantes: MutableList<Estudiante> = mutableListOf()
)