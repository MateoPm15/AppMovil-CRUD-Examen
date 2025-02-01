package com.example.examen_crud

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class ActivityAgregarEditarCurso : AppCompatActivity() {
    private lateinit var controlador: Controlador
    private lateinit var etNombreCurso: EditText
    private lateinit var etDescripcionCurso: EditText
    private lateinit var etDuracionCurso: EditText
    private lateinit var etUbicacionCurso: EditText
    private lateinit var btnUbicacion: Button
    private lateinit var btnGuardarCurso: Button
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var cursoId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_editar_curso)

        controlador = Controlador(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Referencias a los elementos del layout
        etNombreCurso = findViewById(R.id.etNombreCurso)
        etDescripcionCurso = findViewById(R.id.etDescripcionCurso)
        etDuracionCurso = findViewById(R.id.etDuracionCurso)
        etUbicacionCurso = findViewById(R.id.etUbicacionCurso)
        btnUbicacion = findViewById(R.id.btnUbicacion)
        btnGuardarCurso = findViewById(R.id.btnGuardarCurso)

        // Obtener el curso si se está editando
        cursoId = intent.getIntExtra("cursoId", -1).takeIf { it != -1 }
        if (cursoId != null) {
            val curso = controlador.obtenerCursoPorId(cursoId!!)
            curso?.let {
                etNombreCurso.setText(it.nombre)
                etDescripcionCurso.setText(it.descripcion)
                etDuracionCurso.setText(it.duracion.toString())
                etUbicacionCurso.setText(it.ubicacion ?: "0.0, 0.0") // Si es null, usa 0.0, 0.0
            }
        }

        // Evento para el botón de ubicación actual
        btnUbicacion.setOnClickListener {
            obtenerUbicacionActual()
        }

        btnGuardarCurso.setOnClickListener {
            guardarCurso()
        }
    }

    private fun obtenerUbicacionActual() {
        // Verifica permisos
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        } else {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    val coordenadas = "${location.latitude}, ${location.longitude}"
                    etUbicacionCurso.setText(coordenadas)
                    Toast.makeText(this, "Ubicación obtenida correctamente", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "No se pudo obtener la ubicación", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun guardarCurso() {
        val nombre = etNombreCurso.text.toString()
        val descripcion = etDescripcionCurso.text.toString()
        val duracion = etDuracionCurso.text.toString().toIntOrNull()
        val ubicacion = etUbicacionCurso.text.toString()

        if (nombre.isNotEmpty() && descripcion.isNotEmpty() && duracion != null) {
            val curso = Curso(cursoId ?: -1, nombre, descripcion, duracion, ubicacion)
            if (cursoId != null && cursoId != -1) {
                controlador.actualizarCurso(curso)
            } else {
                controlador.crearCurso(curso)
            }
            finish()
        } else {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
        }
    }
}
