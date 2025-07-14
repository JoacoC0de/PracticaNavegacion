package com.example.practicanavegacion.ui.chats

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practicanavegacion.R
import com.example.practicanavegacion.databinding.FragmentChatsListBinding
import com.example.practicanavegacion.model.ChatResponse
import com.example.practicanavegacion.network.RetrofitInstance
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

        val prefs = requireContext().getSharedPreferences("session", android.content.Context.MODE_PRIVATE)
        val userId = prefs.getInt("user_id", 0)

        adapter = ChatAdapter(listOf()) { chat ->
            val appointmentId = chat.id
            val receiverId = chat.workerId
            Log.d("ChatsListFragment", "Abriendo chat: appointmentId=$appointmentId, userId=$userId, workerId=$receiverId")
            val bundle = Bundle().apply {
                putInt("appointmentId", appointmentId)
                putString("workerName", chat.workerName)
                putString("workerPictureUrl", chat.workerPictureUrl ?: "")
                putInt("receiverId", receiverId)
            }
            findNavController().navigate(
                R.id.action_chatsListFragment_to_chatFragment,
                bundle
            )
        }

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
                if (response.isSuccessful && response.body() != null) {
                    adapter.updateList(response.body()!!)
                } else {
                    Toast.makeText(requireContext(), "Error al cargar chats", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}