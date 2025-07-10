package com.example.practicanavegacion.repository

import android.app.Application
import com.example.practicanavegacion.model.ChatResponse
import com.example.practicanavegacion.model.Message
import com.example.practicanavegacion.network.RetrofitInstance
import retrofit2.Response
import android.util.Log

class ChatRepository(application: Application) {
    private val api = RetrofitInstance.getRetrofit(application).create(ApiService::class.java)


    suspend fun getChat(appointmentId: Int): Response<List<Message>> {
        return api.getChat(appointmentId)
    }

    suspend fun sendMessage(appointmentId: Int, content: String, receiverId: Int): Response<Message> {
        val body = mapOf(
            "message" to content,
            "receiver_id" to receiverId.toString()
        )
        return api.sendMessage(appointmentId, body)
    }
}