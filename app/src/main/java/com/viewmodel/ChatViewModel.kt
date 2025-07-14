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
            Log.d("ChatViewModel", "Recuperando mensajes: appointmentId=$appointmentId")
            val response = repo.getChat(appointmentId)
            if (response.isSuccessful) {
                Log.d("ChatViewModel", "Mensajes recibidos: ${response.body()?.size ?: 0}")
                messages.postValue(response.body() ?: emptyList())
            } else {
                Log.e("ChatViewModel", "Error al recuperar mensajes: ${response.code()}")
            }
        }
    }

    fun sendMessage(appointmentId: Int, content: String, receiverId: Int) {
        viewModelScope.launch {
            Log.d("ChatViewModel", "Enviando mensaje: appointmentId=$appointmentId, receiverId=$receiverId, texto=$content")
            val response = repo.sendMessage(appointmentId, content, receiverId)
            if (response.isSuccessful) {
                Log.d("ChatViewModel", "Mensaje enviado correctamente")
                loadChat(appointmentId)
            } else {
                Log.e("ChatViewModel", "Error al enviar mensaje: ${response.code()}")
                val errorMsg = response.errorBody()?.string() ?: "No se pudo enviar el mensaje"
                error.postValue(errorMsg)
            }
        }
    }
}