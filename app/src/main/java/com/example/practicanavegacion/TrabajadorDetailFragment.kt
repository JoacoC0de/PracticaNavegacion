package com.example.practicanavegacion.ui.trabajador
import android.util.Log
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.practicanavegacion.Adapter.ReviewAdapter
import com.example.practicanavegacion.databinding.FragmentTrabajadorDetailBinding
import com.example.practicanavegacion.viewmodel.TrabajadorDetailViewModel
import com.example.practicanavegacion.R
import androidx.navigation.fragment.findNavController
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.practicanavegacion.network.RetrofitInstance
import kotlinx.coroutines.launch

class TrabajadorDetailFragment : Fragment() {
    private var _binding: FragmentTrabajadorDetailBinding? = null
    private val binding get() = _binding!!
    private val args: TrabajadorDetailFragmentArgs by navArgs()
    private val viewModel: TrabajadorDetailViewModel by viewModels()
    private lateinit var reviewAdapter: ReviewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentTrabajadorDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        reviewAdapter = ReviewAdapter()
        binding.rvReviews.layoutManager = LinearLayoutManager(requireContext())
        binding.rvReviews.adapter = reviewAdapter

        viewModel.worker.observe(viewLifecycleOwner) { worker ->
            Log.d("TrabajadorDetail", "Observer worker: $worker")
            if (worker != null) {
                binding.tvFullName.text = "${worker.user.name} ${worker.user.last_name}"
                binding.tvRating.text = "Calificación: ${worker.average_rating}%"
                binding.tvCompletedJobs.text = "Trabajos completados: ${worker.reviews_count}"
                Glide.with(this)
                    .load(worker.picture_url?.takeIf { it != "null" && it.isNotEmpty() })
                    .placeholder(R.drawable.ic_profile_placeholder)
                    .error(R.drawable.ic_profile_placeholder)
                    .into(binding.ivProfile)

                binding.btnContact.setOnClickListener {
                    Log.d("TrabajadorDetail", "Botón Contactar presionado")
                    lifecycleScope.launch {
                        val appointmentId = obtenerOCrearAppointmentId(worker.id)
                        Log.d("TrabajadorDetail", "appointmentId: $appointmentId")
                        if (appointmentId != null && appointmentId > 0) {
                            val workerName = "${worker.user.name} ${worker.user.last_name}"
                            val workerPictureUrl = worker.picture_url ?: ""
                            val action = TrabajadorDetailFragmentDirections.actionTrabajadorDetailFragmentToChatFragment(
                                appointmentId,
                                workerName,
                                workerPictureUrl,
                                worker.id
                            )
                            findNavController().navigate(action)
                        } else {
                            Log.e("TrabajadorDetail", "No se pudo obtener la cita para chatear")
                            Toast.makeText(requireContext(), "No se pudo obtener la cita para chatear", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Log.e("TrabajadorDetail", "El worker es null en el observer")
            }
        }

        viewModel.reviews.observe(viewLifecycleOwner) { reviews ->
            Log.d("TrabajadorDetail", "Reviews recibidas: ${reviews.size}")
            reviewAdapter.updateList(reviews)
        }

        Log.d("TrabajadorDetail", "Llamando a loadWorker con id: ${args.workerId}")
        viewModel.loadWorker(args.workerId)
    }

    private suspend fun obtenerOCrearAppointmentId(workerId: Int): Int? {
        Log.d("TrabajadorDetail", "Llamando a createAppointment con workerId: $workerId")
        val categoryId = args.categoryId
        val api = RetrofitInstance.getRetrofit(requireActivity().application).create(ApiService::class.java)
        val body = mapOf("worker_id" to workerId, "category_selected_id" to categoryId)
        val response = api.createAppointment(body)
        Log.d("TrabajadorDetail", "Respuesta de createAppointment: isSuccessful=${response.isSuccessful}, code=${response.code()}, body=${response.body()}, errorBody=${response.errorBody()?.string()}")
        return if (response.isSuccessful && response.body() != null) response.body()?.id else null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}