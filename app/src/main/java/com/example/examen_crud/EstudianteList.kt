package com.example.examen_crud

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ActivityEstudianteList : AppCompatActivity() {
    private lateinit var controlador: Controlador
    private lateinit var tvCursoTitulo: TextView
    private lateinit var listViewEstudiantes: ListView
    private lateinit var btnAgregarEstudiante: Button
    private var cursoId: Int = 0
    private var selectedEstudiante: Estudiante? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estudiante_list)

        controlador = Controlador(this)
        tvCursoTitulo = findViewById(R.id.tvCursoTitulo)
        listViewEstudiantes = findViewById(R.id.listViewEstudiantes)
        btnAgregarEstudiante = findViewById(R.id.btnAgregarEstudiante)

        // Obtener el ID del curso
        cursoId = intent.getIntExtra("cursoId", 0)

        // Mostrar el nombre del curso en el título
        val curso = controlador.listarCurso().find { it.id == cursoId }
        if (curso != null) {
            tvCursoTitulo.text = curso.nombre
        } else {
            tvCursoTitulo.text = "Curso no encontrado"
        }

        btnAgregarEstudiante.setOnClickListener {
            val intent = Intent(this, ActivityAgregarEditarEstudiante::class.java)
            intent.putExtra("cursoId", cursoId)
            startActivity(intent)
        }

        registerForContextMenu(listViewEstudiantes)
        actualizarLista()
    }

    private fun actualizarLista() {
        val estudiantes = controlador.listarEstudiantesPorCurso(cursoId)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, estudiantes.map { it.nombre })
        listViewEstudiantes.adapter = adapter

        listViewEstudiantes.setOnItemClickListener { _, _, position, _ ->
            selectedEstudiante = estudiantes[position]
            listViewEstudiantes.showContextMenu()
        }
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_menu_estudiante, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuEditarEstudiante -> {
                val intent = Intent(this, ActivityAgregarEditarEstudiante::class.java)
                intent.putExtra("cursoId", cursoId)
                intent.putExtra("estudianteId", selectedEstudiante?.id)
                startActivity(intent)
            }
            R.id.menuEliminarEstudiante -> {
                if (selectedEstudiante != null) {
                    controlador.eliminarEstudiante(selectedEstudiante!!.id)
                    Toast.makeText(this, "Estudiante eliminado", Toast.LENGTH_SHORT).show()
                    actualizarLista()
                }
            }
        }
        return super.onContextItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        actualizarLista()
    }
}

