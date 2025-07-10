package com.example.practicanavegacion

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

class ResenaFragment : Fragment(R.layout.fragment_resena) {
    private val args: ResenaFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tvPregunta = view.findViewById<TextView>(R.id.tvPregunta)
        val ratingBar = view.findViewById<RatingBar>(R.id.ratingBar)
        val etComentario = view.findViewById<EditText>(R.id.etComentario)
        val btnEnviar = view.findViewById<Button>(R.id.btnEnviarResena)
        val btnNuncaVino = view.findViewById<Button>(R.id.btnNuncaVino)

        tvPregunta.text = "¿Qué tal te pareció las soluciones de ${args.nombreTrabajador}?"

        btnEnviar.setOnClickListener {
            val rating = ratingBar.rating.toInt()
            val comentario = etComentario.text.toString()
            Toast.makeText(requireContext(), "¡Reseña enviada!", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }

        btnNuncaVino.setOnClickListener {
            Toast.makeText(requireContext(), "Se reportó que nunca vino.", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }
    }
}