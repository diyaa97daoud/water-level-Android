package com.example.waterlevelmonitoring.data.model

data class UserResponse(
    val id: Long,
    val username: String,
    val email: String,
    val role: Role,
    val createdAt: String
)