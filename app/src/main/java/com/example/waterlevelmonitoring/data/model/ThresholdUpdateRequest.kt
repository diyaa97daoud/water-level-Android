package com.example.waterlevelmonitoring.data.model

data class ThresholdUpdateRequest(
    val minThreshold: Double,
    val maxThreshold: Double
)
