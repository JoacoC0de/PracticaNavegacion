// app/src/main/java/com/example/practicanavegacion/RegisterFragment.kt
package com.example.practicanavegacion
import androidx.navigation.fragment.findNavController

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.practicanavegacion.databinding.FragmentRegisterBinding
import com.example.practicanavegacion.viewmodel.RegisterViewModel
import kotlin.toString

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        setupListeners()
        observeViewModel()
        return binding.root
    }

    private fun setupListeners() {
        binding.btnRegister.setOnClickListener {
            val name = binding.txtName.editText?.text?.toString()?.trim() ?: ""
            val lastName = binding.txtLastName.editText?.text?.toString()?.trim() ?: ""
            val email = binding.txtEmail.editText?.text?.toString()?.trim() ?: ""
            val password = binding.txtPassword.editText?.text?.toString()?.trim() ?: ""

            if (name.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.register(name, lastName, email, password)
        }
    }

    private fun observeViewModel() {
        viewModel.registerResult.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }
        }
        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

}