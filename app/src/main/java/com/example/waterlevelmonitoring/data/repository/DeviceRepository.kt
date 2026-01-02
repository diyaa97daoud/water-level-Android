package com.example.waterlevelmonitoring.data.repository

import com.example.waterlevelmonitoring.data.AuthTokenHolder
import com.example.waterlevelmonitoring.data.model.DeviceResponse
import com.example.waterlevelmonitoring.data.model.Page
import com.example.waterlevelmonitoring.data.model.ThresholdResponse
import com.example.waterlevelmonitoring.data.model.ThresholdUpdateRequest
import com.example.waterlevelmonitoring.data.model.WaterLevelDataResponse
import com.example.waterlevelmonitoring.data.remote.ApiService

class DeviceRepository(private val apiService: ApiService) {

    suspend fun getDevices(): List<DeviceResponse> {
        val token = AuthTokenHolder.token ?: throw Exception("Not authenticated")
        val response = apiService.getDevices("Bearer $token")
        if (response.isSuccessful) {
            return response.body()?.content ?: emptyList()
        } else {
            throw Exception("Failed to fetch devices")
        }
    }

    suspend fun getDevice(deviceId: Long): DeviceResponse {
        val token = "Bearer ${AuthTokenHolder.token}"
        return apiService.getDevice(token, deviceId)
    }

    suspend fun getThresholds(deviceId: Long): ThresholdResponse {
        val token = "Bearer ${AuthTokenHolder.token}"
        return apiService.getThresholds(token, deviceId)
    }

    suspend fun updateThresholds(
        deviceId: Long,
        minThreshold: Double,
        maxThreshold: Double
    ): ThresholdResponse {
        val token = "Bearer ${AuthTokenHolder.token}"
        val request = ThresholdUpdateRequest(minThreshold, maxThreshold)
        return apiService.updateThresholds(token, deviceId, request)
    }

    suspend fun getWaterLevelData(
        deviceId: Long,
        page: Int = 0,
        size: Int = 20
    ): Page<WaterLevelDataResponse> {
        val token = "Bearer ${AuthTokenHolder.token}"
        return apiService.getWaterLevelData(token, deviceId, page, size)
    }
}