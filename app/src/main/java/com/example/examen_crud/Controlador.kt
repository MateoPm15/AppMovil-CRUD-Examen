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
            put("latitud", curso.latitud)
            put("longitud", curso.longitud)
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
            val latitud = cursor.getDouble(4)
            val longitud = cursor.getDouble(5)
            cursos.add(Curso(id, nombre, descripcion, duracion, latitud, longitud))
        }
        cursor.close()
        db.close()
        return cursos
    }

    // Obtener un curso por su ID
    fun obtenerCursoPorId(cursoId: Int): Curso? {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Curso WHERE id = ?", arrayOf(cursoId.toString()))

        var curso: Curso? = null
        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
            val descripcion = cursor.getString(cursor.getColumnIndexOrThrow("descripcion"))
            val duracion = cursor.getInt(cursor.getColumnIndexOrThrow("duracion"))
            val latitud = cursor.getDouble(cursor.getColumnIndexOrThrow("latitud"))
            val longitud = cursor.getDouble(cursor.getColumnIndexOrThrow("longitud"))

            curso = Curso(id, nombre, descripcion, duracion, latitud, longitud)
        }

        cursor.close()
        db.close()
        return curso
    }


    //Actualizar Curso
    fun actualizarCurso(curso: Curso): Boolean {
        val db = dbHelper.writableDatabase
        val valores = ContentValues().apply {
            put("nombre", curso.nombre)
            put("descripcion", curso.descripcion)
            put("duracion", curso.duracion)
            put("latitud", curso.latitud)
            put("longitud", curso.longitud)
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
    fun listarEstudiantesPorCurso(cursoId: Int): List<Estudiante> {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM Estudiante WHERE curso_id = ?", // Actualizado
            arrayOf(cursoId.toString())
        )
        val estudiantes = mutableListOf<Estudiante>()
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
            val edad = cursor.getInt(cursor.getColumnIndexOrThrow("edad"))
            val email = cursor.getString(cursor.getColumnIndexOrThrow("email"))
            val telefono = cursor.getInt(cursor.getColumnIndexOrThrow("telefono"))
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

    fun depurarEstudiantes() {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Estudiante", null)
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
            val cursoId = cursor.getInt(cursor.getColumnIndexOrThrow("curso_id"))
            println("Estudiante: $id, Nombre: $nombre, Curso ID: $cursoId")
        }
        cursor.close()
        db.close()
    }

}