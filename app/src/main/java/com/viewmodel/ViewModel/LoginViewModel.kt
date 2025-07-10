package com.example.practicanavegacion.viewmodel
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.example.practicanavegacion.repository.AuthRepository
import kotlinx.coroutines.launch
import com.example.practicanavegacion.model.LoginResponse

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = AuthRepository(application)
    val loginResult = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val response = repo.login(email, password)
            Log.d("API", "Código de respuesta: ${response.code()}")
            Log.d("API", "Cuerpo de respuesta: ${response.body()}")
            Log.d("API", "ErrorBody: ${response.errorBody()?.string()}")
            if (response.isSuccessful && response.body()?.access_token != null) {
                val prefs = getApplication<Application>().getSharedPreferences("session", Context.MODE_PRIVATE)
                prefs.edit().putString("access_token", response.body()?.access_token).apply()
                Log.d("API", "Conexión exitosa: ${response.body()}")
                loginResult.postValue(true)
            } else {
                Log.e("API", "Error en la conexión: ${response.code()}")
                error.postValue("Credenciales incorrectas")
            }
        }
    }
}