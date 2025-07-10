package com.network

data class RegisterRequest(
    val name: String,
    val lastName: String,
    val email: String,
    val password: String
)