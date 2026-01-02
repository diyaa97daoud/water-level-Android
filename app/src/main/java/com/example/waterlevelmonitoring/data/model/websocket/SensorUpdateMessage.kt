package com.example.waterlevelmonitoring.data.model.websocket

import com.example.waterlevelmonitoring.data.model.PumpStatus

class SensorUpdateMessage(
    type: String,
    deviceId: Long,
    timestamp: String,
    val waterLevel: Double,
    val pumpStatus: PumpStatus
) : BaseMessage(type, deviceId, timestamp)
