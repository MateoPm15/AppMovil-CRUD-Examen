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
        cursoId = intent.getIntExtra("cursoId", 0)

        // Obtener el fragmento del mapa
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Habilitar botones de zoom
        mMap.uiSettings.isZoomControlsEnabled = true

        // Obtener el curso desde la base de datos
        val curso = controlador.obtenerCursoPorId(cursoId)

        curso?.let {
            val ubicacion = LatLng(it.latitud ?: 0.0, it.longitud ?: 0.0)

            // Agregar marcador en la ubicación del curso
            mMap.addMarker(MarkerOptions().position(ubicacion).title(it.nombre))

            // Mover la cámara con zoom
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion, 15f))
        }
    }
}
