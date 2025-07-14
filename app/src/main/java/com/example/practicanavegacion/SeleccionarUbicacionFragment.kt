package com.example.practicanavegacion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class SeleccionarUbicacionFragment : Fragment(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private var marcador: Marker? = null
    private var ubicacionSeleccionada: LatLng? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_seleccionar_ubicacion, container, false)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Botón para mandar ubicación y navegar a seleccionar cita
        view.findViewById<Button>(R.id.btn_mandar_ubicacion).setOnClickListener {
            if (ubicacionSeleccionada != null) {
                // Aquí puedes pasar la ubicación seleccionada como argumento si lo necesitas
                findNavController().navigate(R.id.action_seleccionarUbicacionFragment_to_seleccionarCitaFragment)
            } else {
                Toast.makeText(requireContext(), "Selecciona una ubicación en el mapa", Toast.LENGTH_SHORT).show()
            }
        }

        // Botón de zoom +
        view.findViewById<Button>(R.id.btnZoomIn).setOnClickListener {
            if (::mMap.isInitialized) mMap.animateCamera(CameraUpdateFactory.zoomIn())
        }
        // Botón de zoom -
        view.findViewById<Button>(R.id.btnZoomOut).setOnClickListener {
            if (::mMap.isInitialized) mMap.animateCamera(CameraUpdateFactory.zoomOut())
        }

        return view
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val defaultLatLng = LatLng(-17.768978, -63.182979)
        marcador = mMap.addMarker(
            MarkerOptions()
                .position(defaultLatLng)
                .draggable(true)
                .title("Ubicación seleccionada")
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLatLng, 15f))
        ubicacionSeleccionada = defaultLatLng

        mMap.setOnMapClickListener { latLng ->
            marcador?.position = latLng
            ubicacionSeleccionada = latLng
        }
        mMap.setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener {
            override fun onMarkerDragStart(marker: Marker) {}
            override fun onMarkerDrag(marker: Marker) {}
            override fun onMarkerDragEnd(marker: Marker) {
                ubicacionSeleccionada = marker.position
            }
        })
    }
}