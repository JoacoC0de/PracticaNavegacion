package com.example.practicanavegacion.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.practicanavegacion.model.Trabajador
import com.example.practicanavegacion.repository.TrabajadorRepository
import kotlinx.coroutines.launch

class TrabajadorListViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = TrabajadorRepository(application)
    val trabajadores = MutableLiveData<List<Trabajador>>()
    val error = MutableLiveData<String>()

    fun fetchTrabajadores(categoryId: Int) {
        viewModelScope.launch {
            try {
                val response = repo.getTrabajadoresByCategory(categoryId)
                if (response.isSuccessful && response.body() != null) {
                    trabajadores.postValue(response.body())
                } else {
                    error.postValue("Error al cargar trabajadores: ${response.code()}")
                }
            } catch (e: Exception) {
                error.postValue("Excepción: ${e.localizedMessage}")
            }
        }
    }
}