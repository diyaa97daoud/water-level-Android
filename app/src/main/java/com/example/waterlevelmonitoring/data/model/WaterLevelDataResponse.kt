package com.example.waterlevelmonitoring.data.model

data class WaterLevelDataResponse(
    val id: Long,
    val deviceId: Long,
    val waterLevel: Double,
    val pumpStatus: PumpStatus? = null,
    val timestamp: String,
    val temperature: Double? = null,
    val humidity: Double? = null
)
