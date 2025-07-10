package com.example.practicanavegacion

import CategoryAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practicanavegacion.databinding.FragmentCategoryListBinding
import com.example.practicanavegacion.viewmodel.CategoryListViewModel
import androidx.navigation.fragment.findNavController
import com.example.practicanavegacion.R
import com.example.practicanavegacion.model.Category

class CategoryListFragment : Fragment() {
    private lateinit var binding: FragmentCategoryListBinding
    private val viewModel: CategoryListViewModel by viewModels()
    private lateinit var adapter: CategoryAdapter
    private var allCategories: List<Category> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryListBinding.inflate(inflater, container, false)
        binding.rvCategories.layoutManager = LinearLayoutManager(requireContext())
        adapter = CategoryAdapter(listOf()) { category ->
            val bundle = Bundle().apply { putInt("categoryId", category.id) }
            findNavController().navigate(R.id.action_categoryListFragment_to_trabajadorListFragment, bundle)
        }
        binding.rvCategories.adapter = adapter

        viewModel.categories.observe(viewLifecycleOwner) { categories ->
            Log.d("CategoryListFragment", "Categorías recibidas: ${categories.size}")
            allCategories = categories
            adapter.updateList(categories)
        }

        binding.etSearch.addTextChangedListener { editable ->
            val query = editable.toString().trim()
            val filtered = if (query.isEmpty()) {
                allCategories
            } else {
                allCategories.filter { it.name.contains(query, ignoreCase = true) }
            }
            adapter.updateList(filtered)
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Log.e("CategoryListFragment", "Error: $it")
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        viewModel.fetchCategories()

        binding.fabChats.setOnClickListener {
            findNavController().navigate(R.id.action_categoryListFragment_to_chatsListFragment)
        }

        return binding.root
    }
}