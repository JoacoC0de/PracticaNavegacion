package com.example.practicanavegacion.repository

import ApiService
import android.app.Application
import com.example.practicanavegacion.model.LoginResponse
import com.network.LoginRequest
import com.example.practicanavegacion.network.RetrofitInstance
import retrofit2.Response

class AuthRepository(application: Application) {
    private val api: ApiService = RetrofitInstance.getRetrofit(application)
        .create(ApiService::class.java)

    suspend fun login(email: String, password: String): Response<LoginResponse> {
        val request = LoginRequest(email, password)
        return api.login(request)
    }
}