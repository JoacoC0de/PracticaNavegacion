package com.example.practicanavegacion.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.practicanavegacion.model.Message
import com.example.practicanavegacion.repository.ChatRepository
import kotlinx.coroutines.launch
import android.util.Log

class ChatViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = ChatRepository(application)
    val messages = MutableLiveData<List<Message>>()

    val error = MutableLiveData<String>()

    fun loadChat(appointmentId: Int) {
        viewModelScope.launch {
            val response = repo.getChat(appointmentId)
            if (response.isSuccessful) {
                messages.postValue(response.body() ?: emptyList())
            } else {
            }
        }
    }

    fun sendMessage(appointmentId: Int, content: String, receiverId: Int) {
        viewModelScope.launch {
            try {
                val response = repo.sendMessage(appointmentId, content, receiverId)
                if (response.isSuccessful) {
                    loadChat(appointmentId)
                } else {
                    val errorMsg = response.errorBody()?.string() ?: "No se pudo enviar el mensaje"
                    error.postValue(errorMsg)
                }
            } catch (e: Exception) {
                error.postValue("Error de red o inesperado al enviar mensaje")
            }
        }
    }
}