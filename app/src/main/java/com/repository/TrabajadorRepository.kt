package com.example.practicanavegacion.repository

import android.app.Application
import com.example.practicanavegacion.model.Trabajador
import com.example.practicanavegacion.model.WorkerDetailResponse
import com.example.practicanavegacion.network.RetrofitInstance
import retrofit2.Response

class TrabajadorRepository(application: Application) {
    private val api = RetrofitInstance.getRetrofit(application).create(ApiService::class.java)

    suspend fun getTrabajadoresByCategory(categoryId: Int): Response<List<Trabajador>> {
        return api.getTrabajadoresByCategory(categoryId)
    }

    suspend fun getWorkerDetail(workerId: Int): WorkerDetailResponse {
        return api.getWorkerDetail(workerId)
    }
}