package com.example.practicanavegacion.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.practicanavegacion.model.Category
import com.example.practicanavegacion.repository.CategoryRepository
import kotlinx.coroutines.launch

class CategoryListViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = CategoryRepository(application)
    val categories = MutableLiveData<List<Category>>()
    val error = MutableLiveData<String>()

    fun fetchCategories() {
        viewModelScope.launch {
            try {
                android.util.Log.d("CategoryListViewModel", "Iniciando fetchCategories()")
                val response = repo.getCategories()
                android.util.Log.d("CategoryListViewModel", "Respuesta recibida: ${response.code()} - ${response.body()}")
                if (response.isSuccessful && response.body() != null) {
                    categories.postValue(response.body())
                } else {
                    error.postValue("Error al cargar categorías: ${response.code()}")
                }
            } catch (e: Exception) {
                error.postValue("Excepción: ${e.localizedMessage}")
                android.util.Log.e("CategoryListViewModel", "Excepción: ", e)
            }
        }
    }
}