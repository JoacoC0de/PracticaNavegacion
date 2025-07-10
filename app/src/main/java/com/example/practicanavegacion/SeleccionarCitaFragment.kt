package com.example.practicanavegacion

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class SeleccionarCitaFragment : Fragment(R.layout.fragment_seleccionar_cita) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val datePicker = view.findViewById<DatePicker>(R.id.datePicker)
        val timePicker = view.findViewById<TimePicker>(R.id.timePicker)
        val btnConcretar = view.findViewById<Button>(R.id.btnConcretarCita)

        timePicker.setIs24HourView(true)

        btnConcretar.setOnClickListener {
            val day = datePicker.dayOfMonth
            val month = datePicker.month + 1
            val year = datePicker.year
            val hour = timePicker.hour
            val minute = timePicker.minute

            val nombreTrabajador = "Nombre del Trabajador"

            val action = SeleccionarCitaFragmentDirections
                .actionSeleccionarCitaFragmentToResenaFragment(nombreTrabajador)
            findNavController().navigate(action)
        }
    }
}