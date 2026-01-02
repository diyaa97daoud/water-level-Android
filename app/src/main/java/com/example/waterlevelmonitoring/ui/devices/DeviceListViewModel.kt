package com.example.waterlevelmonitoring.ui.devices

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waterlevelmonitoring.data.model.DeviceResponse
import com.example.waterlevelmonitoring.data.repository.DeviceRepository
import kotlinx.coroutines.launch

class DeviceListViewModel(private val deviceRepository: DeviceRepository) : ViewModel() {

    var devices by mutableStateOf<List<DeviceResponse>>(emptyList())
    var isLoading by mutableStateOf(false)
    var error by mutableStateOf<String?>(null)

    fun getDevices() {
        viewModelScope.launch {
            isLoading = true
            error = null
            try {
                devices = deviceRepository.getDevices()
            } catch (e: Exception) {
                error = e.message
            } finally {
                isLoading = false
            }
        }
    }
}