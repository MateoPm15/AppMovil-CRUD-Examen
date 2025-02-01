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

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val curso = controlador.obtenerCursoPorId(cursoId)

        if (curso != null && curso.latitud != null && curso.longitud != null) {
            val ubicacion = LatLng(curso.latitud, curso.longitud)
            mMap.addMarker(MarkerOptions().position(ubicacion).title("Ubicación del Curso: ${curso.nombre}"))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion, 15f))
        } else {
            Toast.makeText(this, "No hay ubicación guardada para este curso", Toast.LENGTH_SHORT).show()
        }
    }
}
