package com.example.waterlevelmonitoring.data.model.websocket

open class BaseMessage(
    val type: String,
    val deviceId: Long,
    val timestamp: String
)