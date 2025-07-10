package com.example.practicanavegacion.ui.chats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practicanavegacion.databinding.FragmentChatsListBinding
import com.example.practicanavegacion.network.RetrofitInstance
import com.example.practicanavegacion.model.ChatResponse
import kotlinx.coroutines.launch

class ChatsListFragment : Fragment() {
    private var _binding: FragmentChatsListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ChatAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatsListBinding.inflate(inflater, container, false)
        adapter = ChatAdapter(listOf())
        binding.rvChats.layoutManager = LinearLayoutManager(requireContext())
        binding.rvChats.adapter = adapter
        loadChats()
        return binding.root
    }

    private fun loadChats() {
        lifecycleScope.launch {
            try {
                val api = RetrofitInstance.getRetrofit(requireActivity().application)
                    .create(ApiService::class.java)
                val response = api.getUserChats()
                android.util.Log.d("ChatsListFragment", "Respuesta getUserChats: code=${response.code()}, body=${response.body()}")
                if (response.isSuccessful && response.body() != null) {
                    adapter.updateList(response.body()!!)
                } else {
                    android.util.Log.e("ChatsListFragment", "Error al cargar chats: ${response.errorBody()?.string()}")
                    Toast.makeText(requireContext(), "Error al cargar chats", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                android.util.Log.e("ChatsListFragment", "Excepción: ${e.localizedMessage}", e)
                Toast.makeText(requireContext(), "Error: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}