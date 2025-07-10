package com.example.practicanavegacion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.practicanavegacion.databinding.FragmentLoginBinding
import com.example.practicanavegacion.viewmodel.LoginViewModel

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        setupEventListeners()
        observeViewModel()
        return binding.root
    }

    private fun setupEventListeners() {
        binding.btnSignIn.setOnClickListener {
            val email = binding.txtEmailLogin.editText?.text.toString()
            val password = binding.txtPasswordLogin.editText?.text.toString()
            viewModel.login(email, password)
        }
    }

    private fun observeViewModel() {
        viewModel.loginResult.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(R.id.action_loginFragment_to_categoryListFragment)
            }
        }
        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }
}