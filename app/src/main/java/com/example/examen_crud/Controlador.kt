package com.example.examen_crud

import android.content.ContentValues
import android.content.Context

class Controlador (context: Context) {
    private val dbHelper = SqliteHelper(context)

    //Crear Curso
    fun crearCurso(curso: Curso) {
        val db = dbHelper.writableDatabase
        val valores = ContentValues().apply {
            put("id", curso.id)
            put("nombre", curso.nombre)
            put("descripcion", curso.descripcion)
            put("duracion", curso.duracion)
        }
        db.insert("Curso", null, valores)
        db.close()
    }

    //Listar Cursos
    fun listarCurso(): List<Curso> {
        val db = dbHelper.writableDatabase
        val cursor = db.rawQuery("SELECT * FROM Curso", null)
        val cursos = mutableListOf<Curso>()
        while (cursor.moveToNext()) {
            val id = cursor.getInt(0)
            val nombre = cursor.getString(1)
            val descripcion = cursor.getString(2)
            val duracion = cursor.getInt(3)
            cursos.add(Curso(id, nombre, descripcion, duracion))
        }
        cursor.close()
        db.close()
        return cursos
    }

    //Actualizar Curso
    fun actualizarCurso(curso: Curso): Boolean {
        val db = dbHelper.writableDatabase
        val valores = ContentValues().apply {
            put("nombre", curso.nombre)
            put("descripcion", curso.descripcion)
            put("duracion", curso.duracion)
        }
        val rows = db.update(
            "Curso",
            valores,
            "id = ?",
            arrayOf(curso.id.toString())
        )
        db.close()
        return rows > 0
    }

    //Eliminar Curso
    fun eliminarCurso (cursoId: Int): Boolean {
        val db = dbHelper.writableDatabase
        val rows = db.delete(
            "Curso",
            "id = ?",
            arrayOf(cursoId.toString())
        )
        db.close()
        return rows > 0
    }

    //Crear Estudiante
    fun crearEstudiante(cursoId: Int, estudiante: Estudiante) {
        val db = dbHelper.writableDatabase
        val valores = ContentValues().apply {
            put("id", estudiante.id)
            put("nombre", estudiante.nombre)
            put("edad", estudiante.edad)
            put("email", estudiante.email)
            put("telefono", estudiante.telefono)
            put("curso_id", cursoId)
        }
        db.insert("Estudiante", null, valores)
        db.close()
    }

    //Listar estudiantes de un curso
    fun listarEstudiantesPorCurso(cursoId: Int):List<Estudiante> {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM Estudiante WHERE curso_id = ?",
            arrayOf(cursoId.toString())
        )
        val estudiantes = mutableListOf<Estudiante>()
        while (cursor.moveToNext()) {
            val id = cursor.getInt(0)
            val nombre = cursor.getString(1)
            val edad = cursor.getInt(2)
            val email = cursor.getString(3)
            val telefono = cursor.getInt(4)
            estudiantes.add(Estudiante(id, nombre, edad, email, telefono))
        }
        cursor.close()
        db.close()
        return estudiantes
    }

    //Actualizar Estudiante
    fun actualizarEstudiante(estudiante: Estudiante): Boolean {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nombre", estudiante.nombre)
            put("edad", estudiante.edad)
            put("email", estudiante.email)
            put("telefono", estudiante.telefono)
        }
        val rows = db.update("Estudiante", values, "id = ?", arrayOf(estudiante.id.toString()))
        db.close()
        return rows > 0
    }

    // Eliminar Estudiante
    fun eliminarEstudiante(estudianteId: Int): Boolean {
        val db = dbHelper.writableDatabase
        val rows = db.delete("Estudiante", "id = ?", arrayOf(estudianteId.toString()))
        db.close()
        return rows > 0
    }
}