package com.example.examen_crud

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class Mapa : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var controlador: Controlador
    private var cursoId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapa)

        controlador = Controlador(this)
        cursoId = intent.getIntExtra("cursoId", -1) // Verifica si el cursoId es válido

        if (cursoId == -1) {
            Toast.makeText(this, "Error: Curso no encontrado", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        // Obtener el fragmento del mapa
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Habilitar controles de zoom y brújula
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isCompassEnabled = true

        // Obtener el curso desde la base de datos
        val curso = controlador.obtenerCursoPorId(cursoId)

        curso?.let {
            if (!it.ubicacion.isNullOrEmpty() && it.ubicacion.contains(",")) {
                try {
                    // Dividir la cadena de ubicación en latitud y longitud
                    val ubicacionArray = it.ubicacion.split(",")
                    val latitud = ubicacionArray[0].trim().toDouble()
                    val longitud = ubicacionArray[1].trim().toDouble()
                    val ubicacion = LatLng(latitud, longitud)

                    // Agregar marcador en la ubicación del curso
                    mMap.addMarker(MarkerOptions().position(ubicacion).title(it.nombre))

                    // Mover la cámara con zoom
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion, 15f))
                } catch (e: Exception) {
                    Toast.makeText(this, "Error al procesar la ubicación", Toast.LENGTH_SHORT).show()
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(0.0, 0.0), 2f))
                }
            } else {
                Toast.makeText(this, "Ubicación no disponible para este curso", Toast.LENGTH_SHORT).show()
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(0.0, 0.0), 2f))
            }
        } ?: run {
            Toast.makeText(this, "No se encontró información del curso", Toast.LENGTH_SHORT).show()
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(0.0, 0.0), 2f))
        }
    }
}
