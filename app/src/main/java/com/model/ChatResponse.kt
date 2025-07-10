package com.example.practicanavegacion.model

data class ChatResponse(
    val id: Int,
    val workerName: String,
    val lastMessage: String,
    val workerPictureUrl: String?
)