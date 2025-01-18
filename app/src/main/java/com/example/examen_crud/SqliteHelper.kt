package com.example.examen_crud

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqliteHelper(context: Context?) : SQLiteOpenHelper(
    context,
    "CursosDB",
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSqlCrearCurso = """
            CREATE TABLE Curso (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre VARCHAR(250),
                descripcion VARCHAR(250),
                duracion INTEGER
            )
        """.trimIndent()
        db?.execSQL(scriptSqlCrearCurso)

        val scriptSqlCrearEstudiante = """
            CREATE TABLE Estudiante (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre VARCHAR(255),
                edad INTEGER,
                email VARCHAR(255),
                telefono VARCHAR(15),
                curso_id INTEGER,
                FOREIGN KEY (curso_id) REFERENCES Curso(id) ON DELETE CASCADE
            )
        """.trimIndent()
        db?.execSQL(scriptSqlCrearEstudiante)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

}