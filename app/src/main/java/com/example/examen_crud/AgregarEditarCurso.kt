package com.example.examen_crud

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ActivityAgregarEditarCurso : AppCompatActivity() {
    private lateinit var controlador: Controlador
    private lateinit var etNombreCurso: EditText
    private lateinit var etDescripcionCurso: EditText
    private lateinit var etDuracionCurso: EditText
    private lateinit var btnGuardarCurso: Button
    private var cursoId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_editar_curso)

        controlador = Controlador(this)
        etNombreCurso = findViewById(R.id.etNombreCurso)
        etDescripcionCurso = findViewById(R.id.etDescripcionCurso)
        etDuracionCurso = findViewById(R.id.etDuracionCurso)
        btnGuardarCurso = findViewById(R.id.btnGuardarCurso)
        val tvFormularioTitulo: TextView = findViewById(R.id.tvFormularioTitulo)
        tvFormularioTitulo.text = if (cursoId != null) "Editar Curso" else "Agregar Curso"

        cursoId = intent.getIntExtra("cursoId", 0).takeIf { it != 0 }

        if (cursoId != null) {
            val curso = controlador.listarCurso().find { it.id == cursoId }
            curso?.let {
                etNombreCurso.setText(it.nombre)
                etDescripcionCurso.setText(it.descripcion)
                etDuracionCurso.setText(it.duracion.toString())
            }
        }

        btnGuardarCurso.setOnClickListener {
            val nombre = etNombreCurso.text.toString()
            val descripcion = etDescripcionCurso.text.toString()
            val duracion = etDuracionCurso.text.toString().toIntOrNull()

            if (nombre.isNotEmpty() && descripcion.isNotEmpty() && duracion != null) {
                if (cursoId != null) {
                    controlador.actualizarCurso(Curso(cursoId!!, nombre, descripcion, duracion))
                    Toast.makeText(this, "Curso actualizado", Toast.LENGTH_SHORT).show()
                } else {
                    val nuevoId = (controlador.listarCurso().maxOfOrNull { it.id } ?: 0) + 1
                    controlador.crearCurso(Curso(nuevoId, nombre, descripcion, duracion))
                    Toast.makeText(this, "Curso creado", Toast.LENGTH_SHORT).show()
                }
                finish()
            } else {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
