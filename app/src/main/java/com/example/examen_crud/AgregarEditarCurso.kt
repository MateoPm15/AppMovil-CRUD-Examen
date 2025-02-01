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
    private lateinit var etLatitudCurso: EditText
    private lateinit var etLongitudCurso: EditText
    private lateinit var btnGuardarCurso: Button
    private var cursoId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_editar_curso)

        controlador = Controlador(this)

        // Referencias a los EditText
        etNombreCurso = findViewById(R.id.etNombreCurso)
        etDescripcionCurso = findViewById(R.id.etDescripcionCurso)
        etDuracionCurso = findViewById(R.id.etDuracionCurso)
        etLatitudCurso = findViewById(R.id.etLatitudCurso)
        etLongitudCurso = findViewById(R.id.etLongitudCurso)
        btnGuardarCurso = findViewById(R.id.btnGuardarCurso)
        val tvFormularioTitulo: TextView = findViewById(R.id.tvFormularioTitulo)

        // Obtener cursoId de intent
        cursoId = intent.getIntExtra("cursoId", 0).takeIf { it != 0 }

        // Modificar el título del formulario dependiendo de si se edita o se crea un curso
        tvFormularioTitulo.text = if (cursoId != null) "Editar Curso" else "Agregar Curso"

        // Si estamos editando, llenar los campos con la información del curso
        if (cursoId != null) {
            val curso = controlador.obtenerCursoPorId(cursoId!!)
            curso?.let {
                etNombreCurso.setText(it.nombre)
                etDescripcionCurso.setText(it.descripcion)
                etDuracionCurso.setText(it.duracion.toString())
                etLatitudCurso.setText(it.latitud?.toString() ?: "")
                etLongitudCurso.setText(it.longitud?.toString() ?: "")
            }
        }

        // Acción del botón guardar
        btnGuardarCurso.setOnClickListener {
            guardarCurso()
        }
    }

    private fun guardarCurso() {
        val nombre = etNombreCurso.text.toString()
        val descripcion = etDescripcionCurso.text.toString()
        val duracion = etDuracionCurso.text.toString().toIntOrNull()
        val latitud = etLatitudCurso.text.toString().toDoubleOrNull()
        val longitud = etLongitudCurso.text.toString().toDoubleOrNull()

        if (nombre.isNotEmpty() && descripcion.isNotEmpty() && duracion != null) {
            if (cursoId != null) {
                // Actualizar curso existente
                controlador.actualizarCurso(
                    Curso(cursoId!!, nombre, descripcion, duracion, latitud, longitud)
                )
                Toast.makeText(this, "Curso actualizado", Toast.LENGTH_SHORT).show()
            } else {
                // Crear nuevo curso
                controlador.crearCurso(
                    Curso(0, nombre, descripcion, duracion, latitud, longitud)
                )
                Toast.makeText(this, "Curso creado", Toast.LENGTH_SHORT).show()
            }
            finish()
        } else {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
        }
    }
}
