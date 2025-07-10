package com.example.practicanavegacion.model

data class Review(
    val id: Int,
    val rating: Double,
    val comment: String,
    val client_name: String,
    val created_at: String
)