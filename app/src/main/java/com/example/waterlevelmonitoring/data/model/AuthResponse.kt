package com.example.waterlevelmonitoring.data.model

data class AuthResponse(
    val token: String,
    val type: String = "Bearer",
    val expiresIn: Long,
    val user: UserResponse
)