// ui/MapFragment.kt
package com.example.practicanavegacion.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.practicanavegacion.R

class MapFragment : Fragment(), OnMapReadyCallback {
    private var selectedLatLng: LatLng? = null
    private lateinit var map: GoogleMap

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        view.findViewById<View>(R.id.btnConfirmLocation).setOnClickListener {
            selectedLatLng?.let {
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.setOnMapClickListener { latLng ->
            map.clear()
            map.addMarker(MarkerOptions().position(latLng).title("Ubicación de trabajo"))
            selectedLatLng = latLng
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f))
        }
    }
}