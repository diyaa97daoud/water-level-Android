package com.example.waterlevelmonitoring.data.repository

import com.example.waterlevelmonitoring.data.model.AuthRequest
import com.example.waterlevelmonitoring.data.model.AuthResponse
import com.example.waterlevelmonitoring.data.remote.ApiService

class AuthRepository(private val apiService: ApiService) {

    suspend fun login(authRequest: AuthRequest): AuthResponse {
        return apiService.login(authRequest)
    }
}