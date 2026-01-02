package com.example.waterlevelmonitoring.data.model.websocket

class ThresholdUpdateMessage(
    type: String,
    deviceId: Long,
    val minThreshold: Double,
    val maxThreshold: Double
) : BaseMessage(type, deviceId, "")
