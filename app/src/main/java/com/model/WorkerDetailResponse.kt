package com.example.practicanavegacion.model

data class WorkerDetailResponse(
    val id: Int,
    val user_id: Int,
    val picture_url: String?,
    val average_rating: Double,
    val reviews_count: Int,
    val user: User,
    val categories: List<Category>,
    val reviews: List<Review>
)