package com.example.practicanavegacion.model

data class Message(
    val id: Int,
    val appointment_id: Int,
    val sender_id: Int,
    val receiver_id: Int,
    val date_sent: String,
    val message: String
)