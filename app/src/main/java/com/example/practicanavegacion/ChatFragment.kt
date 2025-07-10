package com.example.practicanavegacion.ui

import android.util.Log
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.practicanavegacion.R
import com.example.practicanavegacion.databinding.FragmentChatBinding
import com.example.practicanavegacion.ui.adapter.MessageAdapter
import com.example.practicanavegacion.viewmodel.ChatViewModel

class ChatFragment : Fragment() {
    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ChatViewModel by viewModels()
    private val args: ChatFragmentArgs by navArgs()
    private lateinit var messageAdapter: MessageAdapter
    private val handler = Handler(Looper.getMainLooper())
    private val refreshRunnable = object : Runnable {
        override fun run() {
            viewModel.loadChat(args.appointmentId)
            handler.postDelayed(this, 30000)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("ChatFragment", "Args recibidos: appointmentId=${args.appointmentId}, workerName=${args.workerName}")

        if (args.appointmentId == 0 || args.workerName.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Faltan datos para el chat", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
            return
        }

        binding.tvName.text = args.workerName
        Glide.with(this)
            .load(args.workerPictureUrl.takeIf { it != "null" && it.isNotEmpty() })
            .placeholder(R.drawable.ic_profile_placeholder)
            .error(R.drawable.ic_profile_placeholder)
            .into(binding.ivProfile)

        messageAdapter = MessageAdapter()
        binding.rvMessages.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = messageAdapter
        }

        viewModel.messages.observe(viewLifecycleOwner) { messages ->
            Log.d("ChatFragment", "Mensajes recibidos: ${messages.size}")
            messageAdapter.submitList(messages)
            binding.rvMessages.scrollToPosition(messages.size - 1)
        }
        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        binding.btnSend.setOnClickListener {
            val text = binding.etMessage.text.toString().trim()
            if (text.isNotEmpty()) {
                viewModel.sendMessage(args.appointmentId, text, args.receiverId)
                binding.etMessage.setText("")
            }
        }

        binding.btnCita.setOnClickListener {
            try {
                findNavController().navigate(R.id.action_chatFragment_to_seleccionarCitaFragment)
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error al navegar: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
                Log.e("ChatFragment", "Error al navegar", e)
            }
        }

        viewModel.loadChat(args.appointmentId)
        Log.d("ChatFragment", "Llamando a loadChat con appointmentId=${args.appointmentId}")
        handler.postDelayed(refreshRunnable, 30000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(refreshRunnable)
        _binding = null
    }
}