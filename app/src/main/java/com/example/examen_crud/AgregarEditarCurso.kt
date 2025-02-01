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

        // Enlazar los elementos del layout
        etNombreCurso = findViewById(R.id.etNombreCurso)
        etDescripcionCurso = findViewById(R.id.etDescripcionCurso)
        etDuracionCurso = findViewById(R.id.etDuracionCurso)
        etLatitudCurso = findViewById(R.id.etLatitudCurso)
        etLongitudCurso = findViewById(R.id.etLongitudCurso)
        btnGuardarCurso = findViewById(R.id.btnGuardarCurso)
        val tvFormularioTitulo: TextView = findViewById(R.id.tvFormularioTitulo)

        // Obtener el cursoId del intent
        cursoId = intent.getIntExtra("cursoId", -1).takeIf { it != -1 }

        println("Recibido cursoId: $cursoId")  // Para depuración

        if (cursoId != null) {
            tvFormularioTitulo.text = "Editar Curso"
            val curso = controlador.obtenerCursoPorId(cursoId!!)

            if (curso != null) {
                println("Curso encontrado: $curso")  // Para depuración

                // Llenar los campos con la información del curso
                etNombreCurso.setText(curso.nombre)
                etDescripcionCurso.setText(curso.descripcion)
                etDuracionCurso.setText(curso.duracion.toString())
                etLatitudCurso.setText(curso.latitud?.toString() ?: "0.0")
                etLongitudCurso.setText(curso.longitud?.toString() ?: "0.0")
            } else {
                println("Curso no encontrado en la base de datos")
                Toast.makeText(this, "Error: Curso no encontrado", Toast.LENGTH_LONG).show()
            }
        } else {
            tvFormularioTitulo.text = "Agregar Curso"
        }

        // Guardar curso al presionar el botón
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

        if (nombre.isNotEmpty() && descripcion.isNotEmpty() && duracion != null && latitud != null && longitud != null) {
            if (cursoId != null) {
                // Actualizar curso existente
                val cursoActualizado = Curso(cursoId!!, nombre, descripcion, duracion, latitud, longitud)
                controlador.actualizarCurso(cursoActualizado)
                Toast.makeText(this, "Curso actualizado", Toast.LENGTH_SHORT).show()
            } else {
                // Crear nuevo curso
                val nuevoId = (controlador.listarCurso().maxOfOrNull { it.id } ?: 0) + 1
                controlador.crearCurso(Curso(nuevoId, nombre, descripcion, duracion, latitud, longitud))
                Toast.makeText(this, "Curso creado", Toast.LENGTH_SHORT).show()
            }
            finish()
        } else {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
        }
    }
}
