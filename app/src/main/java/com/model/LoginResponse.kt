package com.example.practicanavegacion.model

data class LoginResponse(
    val access_token: String?,
    val success: Boolean? = null,
    val message: String? = null
)