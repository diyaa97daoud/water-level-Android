package com.example.waterlevelmonitoring.data.model

data class DeviceResponse(
    val id: Long,
    val name: String,
    val deviceKey: String,
    val minThreshold: Double,
    val maxThreshold: Double,
    val adminId: Long,
    val adminUsername: String,
    val createdAt: String,
    val updatedAt: String
)