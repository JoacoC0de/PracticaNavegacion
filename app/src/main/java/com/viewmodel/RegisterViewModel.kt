
package com.example.practicanavegacion.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.util.Log
import com.network.RegisterRequest



class RegisterViewModel : ViewModel() {
    private val _registerResult = MutableLiveData<Boolean>()
    val registerResult: LiveData<Boolean> = _registerResult

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val api = Retrofit.Builder()
        .baseUrl("http://trabajos.jmacboy.com/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)

    fun register(name: String, lastName: String, email: String, password: String) {
        _loading.value = true
        viewModelScope.launch {
            try {
                Log.d("RegisterViewModel", "Intentando registrar: $name $lastName $email")
                val request = RegisterRequest(name, lastName, email, password)
                val response = api.register(request)
                Log.d("RegisterViewModel", "Código de respuesta: ${response.code()}")
                if (response.isSuccessful) {
                    Log.d("RegisterViewModel", "Registro exitoso: ${response.body()}")
                    _registerResult.value = true
                } else {
                    val errorMsg = response.errorBody()?.string() ?: "Error al registrar usuario"
                    Log.e("RegisterViewModel", "Error de API: $errorMsg")
                    _error.value = errorMsg
                }
            } catch (e: Exception) {
                Log.e("RegisterViewModel", "Excepción: ${e.localizedMessage}", e)
                _error.value = "Error de red: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }
}