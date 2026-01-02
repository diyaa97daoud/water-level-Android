package com.example.waterlevelmonitoring.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.waterlevelmonitoring.data.repository.DeviceRepository

class DeviceDetailsViewModelFactory(private val deviceRepository: DeviceRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DeviceDetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DeviceDetailsViewModel(deviceRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
