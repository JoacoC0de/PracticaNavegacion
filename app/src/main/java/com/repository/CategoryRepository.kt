package com.example.practicanavegacion.repository

import ApiService
import android.app.Application
import com.example.practicanavegacion.model.Category
import com.example.practicanavegacion.network.RetrofitInstance
import retrofit2.Response

class CategoryRepository(application: Application) {
    private val api: ApiService = RetrofitInstance.getRetrofit(application)
        .create(ApiService::class.java)

    suspend fun getCategories(): Response<List<Category>> {
        android.util.Log.d("CategoryRepository", "Llamando a getCategories() de ApiService")
        val response = api.getCategories()
        android.util.Log.d("CategoryRepository", "Respuesta de la API: ${response.code()} - ${response.body()}")
        return response
    }
}