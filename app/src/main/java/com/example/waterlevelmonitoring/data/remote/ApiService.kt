package com.example.waterlevelmonitoring.data.remote

import com.example.waterlevelmonitoring.data.model.AuthRequest
import com.example.waterlevelmonitoring.data.model.AuthResponse
import com.example.waterlevelmonitoring.data.model.DeviceResponse
import com.example.waterlevelmonitoring.data.model.Page
import com.example.waterlevelmonitoring.data.model.ThresholdResponse
import com.example.waterlevelmonitoring.data.model.ThresholdUpdateRequest
import com.example.waterlevelmonitoring.data.model.WaterLevelDataResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("auth/login")
    suspend fun login(@Body authRequest: AuthRequest): AuthResponse

    @GET("devices")
    suspend fun getDevices(@Header("Authorization") token: String): retrofit2.Response<Page<DeviceResponse>>

    @GET("devices/{deviceId}")
    suspend fun getDevice(
        @Header("Authorization") token: String,
        @Path("deviceId") deviceId: Long
    ): DeviceResponse

    @GET("devices/{deviceId}/thresholds")
    suspend fun getThresholds(
        @Header("Authorization") token: String,
        @Path("deviceId") deviceId: Long
    ): ThresholdResponse

    @PUT("devices/{deviceId}/thresholds")
    suspend fun updateThresholds(
        @Header("Authorization") token: String,
        @Path("deviceId") deviceId: Long,
        @Body request: ThresholdUpdateRequest
    ): ThresholdResponse

    @GET("devices/{deviceId}/water-level-data")
    suspend fun getWaterLevelData(
        @Header("Authorization") token: String,
        @Path("deviceId") deviceId: Long,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 20
    ): Page<WaterLevelDataResponse>
}