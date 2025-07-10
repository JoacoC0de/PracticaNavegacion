package com.example.practicanavegacion.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.practicanavegacion.model.Trabajador
import com.example.practicanavegacion.model.Review
import com.example.practicanavegacion.model.WorkerDetailResponse
import com.example.practicanavegacion.network.RetrofitInstance
import kotlinx.coroutines.launch

class TrabajadorDetailViewModel(application: Application) : AndroidViewModel(application) {
    val worker = MutableLiveData<Trabajador?>()
    val reviews = MutableLiveData<List<Review>>()

    fun loadWorker(workerId: Int) {
        Log.d("TrabajadorDetailVM", "Iniciando carga de worker con id: $workerId")
        viewModelScope.launch {
            try {
                val api = RetrofitInstance.getRetrofit(getApplication()).create(ApiService::class.java)
                val response: WorkerDetailResponse = api.getWorkerDetail(workerId)
                Log.d("TrabajadorDetailVM", "WorkerDetailResponse recibido: $response")
                val trabajador = Trabajador(
                    id = response.id,
                    user_id = response.user_id,
                    picture_url = response.picture_url,
                    average_rating = response.average_rating,
                    reviews_count = response.reviews_count,
                    user = response.user
                )
                worker.postValue(trabajador)
                reviews.postValue(response.reviews)
            } catch (e: Exception) {
                Log.e("TrabajadorDetailVM", "Error al cargar worker: ${e.message}", e)
                worker.postValue(null)
                reviews.postValue(emptyList())
            }
        }
    }
}