package com.example.practicanavegacion.ui.trabajador

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practicanavegacion.Adapter.TrabajadorAdapter
import com.example.practicanavegacion.databinding.FragmentTrabajadorListBinding
import com.example.practicanavegacion.viewmodel.TrabajadorListViewModel
import com.example.practicanavegacion.model.Trabajador
import androidx.navigation.fragment.findNavController
import com.example.practicanavegacion.ui.trabajador.TrabajadorListFragmentDirections

class TrabajadorListFragment : Fragment() {
    private lateinit var binding: FragmentTrabajadorListBinding
    private val viewModel: TrabajadorListViewModel by viewModels()
    private lateinit var adapter: TrabajadorAdapter
    private var allTrabajadores: List<Trabajador> = listOf()
    private var categoryId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoryId = arguments?.getInt("categoryId") ?: 0
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentTrabajadorListBinding.inflate(inflater, container, false)
        binding.rvTrabajadores.layoutManager = LinearLayoutManager(requireContext())
        adapter = TrabajadorAdapter(listOf()) { trabajador ->
            val action = TrabajadorListFragmentDirections
                .actionTrabajadorListFragmentToTrabajadorDetailFragment(trabajador.id, categoryId)
            findNavController().navigate(action)
        }
        binding.rvTrabajadores.adapter = adapter

        viewModel.trabajadores.observe(viewLifecycleOwner) { trabajadores ->
            allTrabajadores = trabajadores
            adapter.updateList(trabajadores)
        }

        binding.etSearch.addTextChangedListener { editable ->
            val query = editable.toString().trim()
            val filtered = if (query.isEmpty()) {
                allTrabajadores
            } else {
                allTrabajadores.filter {
                    val nombreCompleto = "${it.user.name} ${it.user.last_name}"
                    nombreCompleto.contains(query, ignoreCase = true)
                }
            }
            adapter.updateList(filtered)
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        viewModel.fetchTrabajadores(categoryId)
        return binding.root
    }
}