package com.example.practicanavegacion.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.practicanavegacion.databinding.ItemTrabajadorBinding
import com.example.practicanavegacion.model.Trabajador

class TrabajadorAdapter(
    private var trabajadores: List<Trabajador>,
    private val onItemClick: (Trabajador) -> Unit
) : RecyclerView.Adapter<TrabajadorAdapter.TrabajadorViewHolder>() {

    inner class TrabajadorViewHolder(val binding: ItemTrabajadorBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(trabajador: Trabajador) {
            val user = trabajador.user
            if (user != null) {
                val nombreCompleto = "${user.name} ${user.last_name}"
                binding.tvTrabajadorName.text = nombreCompleto
            } else {
                binding.tvTrabajadorName.text = "Sin nombre"
            }
            binding.tvTrabajadorRating.text = "Calificación: ${trabajador.average_rating}%"
            binding.tvTrabajadorJobs.text = "Trabajos: ${trabajador.reviews_count}"

            val url = if (trabajador.picture_url != null && trabajador.picture_url != "null") trabajador.picture_url else null
            Glide.with(binding.ivTrabajadorPhoto.context)
                .load(url)
                .placeholder(android.R.drawable.ic_menu_report_image)
                .error(android.R.drawable.ic_menu_report_image)
                .into(binding.ivTrabajadorPhoto)

            binding.root.setOnClickListener { onItemClick(trabajador) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrabajadorViewHolder {
        val binding = ItemTrabajadorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrabajadorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrabajadorViewHolder, position: Int) {
        holder.bind(trabajadores[position])
    }

    override fun getItemCount() = trabajadores.size

    fun updateList(newList: List<Trabajador>) {
        trabajadores = newList
        notifyDataSetChanged()
    }
}