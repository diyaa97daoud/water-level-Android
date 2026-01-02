package com.example.waterlevelmonitoring.data.model

data class WaterLevelDataResponse(
    val id: Long,
    val deviceId: Long,
    val waterLevel: Double,
    val pumpStatus: PumpStatus,
    val timestamp: String
)
