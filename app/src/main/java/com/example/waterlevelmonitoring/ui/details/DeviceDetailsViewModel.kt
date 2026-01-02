package com.example.waterlevelmonitoring.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waterlevelmonitoring.data.model.DeviceResponse
import com.example.waterlevelmonitoring.data.model.WaterLevelDataResponse
import com.example.waterlevelmonitoring.data.repository.DeviceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class DeviceDetailsUiState(
    val device: DeviceResponse? = null,
    val waterLevelData: List<WaterLevelDataResponse> = emptyList(),
    val minThreshold: String = "",
    val maxThreshold: String = "",
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val isUpdatingThresholds: Boolean = false,
    val isLoadingMore: Boolean = false,
    val error: String? = null,
    val thresholdError: String? = null,
    val minThresholdError: String? = null,
    val maxThresholdError: String? = null,
    val successMessage: String? = null,
    val currentPage: Int = 0,
    val hasMore: Boolean = true
)

class DeviceDetailsViewModel(private val deviceRepository: DeviceRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(DeviceDetailsUiState())
    val uiState: StateFlow<DeviceDetailsUiState> = _uiState.asStateFlow()

    fun loadDeviceDetails(deviceId: Long) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                // Load device info first (this is critical)
                val device = deviceRepository.getDevice(deviceId)
                
                // Try to load thresholds (fallback to device thresholds if API fails)
                val thresholds = try {
                    deviceRepository.getThresholds(deviceId)
                } catch (e: Exception) {
                    // Use device's thresholds as fallback
                    com.example.waterlevelmonitoring.data.model.ThresholdResponse(
                        device.minThreshold,
                        device.maxThreshold
                    )
                }
                
                // Try to load water level data (optional - may be empty)
                val waterLevelData = try {
                    deviceRepository.getWaterLevelData(deviceId, 0, 10)
                } catch (e: Exception) {
                    // Return empty page if no data exists yet
                    com.example.waterlevelmonitoring.data.model.Page(
                        content = emptyList(),
                        last = true,
                        totalPages = 0,
                        totalElements = 0,
                        size = 0,
                        number = 0,
                        first = true,
                        numberOfElements = 0,
                        empty = true
                    )
                }

                _uiState.value = _uiState.value.copy(
                    device = device,
                    minThreshold = thresholds.minThreshold.toString(),
                    maxThreshold = thresholds.maxThreshold.toString(),
                    waterLevelData = waterLevelData.content,
                    hasMore = !waterLevelData.last,
                    currentPage = 0,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to load device: ${e.message}",
                    isLoading = false
                )
            }
        }
    }

    fun refreshData(deviceId: Long) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isRefreshing = true, error = null)
            try {
                // Reload device info
                val device = deviceRepository.getDevice(deviceId)
                
                // Reload thresholds
                val thresholds = try {
                    deviceRepository.getThresholds(deviceId)
                } catch (e: Exception) {
                    com.example.waterlevelmonitoring.data.model.ThresholdResponse(
                        device.minThreshold,
                        device.maxThreshold
                    )
                }
                
                // Reload first page of water level data
                val waterLevelData = try {
                    deviceRepository.getWaterLevelData(deviceId, 0, 10)
                } catch (e: Exception) {
                    com.example.waterlevelmonitoring.data.model.Page(
                        content = emptyList(),
                        last = true,
                        totalPages = 0,
                        totalElements = 0,
                        size = 0,
                        number = 0,
                        first = true,
                        numberOfElements = 0,
                        empty = true
                    )
                }

                _uiState.value = _uiState.value.copy(
                    device = device,
                    minThreshold = thresholds.minThreshold.toString(),
                    maxThreshold = thresholds.maxThreshold.toString(),
                    waterLevelData = waterLevelData.content,
                    hasMore = !waterLevelData.last,
                    currentPage = 0,
                    isRefreshing = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to refresh: ${e.message}",
                    isRefreshing = false
                )
            }
        }
    }

    fun loadMoreData(deviceId: Long) {
        if (_uiState.value.isLoadingMore || !_uiState.value.hasMore) return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoadingMore = true)
            try {
                val nextPage = _uiState.value.currentPage + 1
                val result = deviceRepository.getWaterLevelData(deviceId, nextPage, 10)

                _uiState.value = _uiState.value.copy(
                    waterLevelData = _uiState.value.waterLevelData + result.content,
                    currentPage = nextPage,
                    hasMore = !result.last,
                    isLoadingMore = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to load more data: ${e.message}",
                    isLoadingMore = false
                )
            }
        }
    }

    fun onMinThresholdChange(value: String) {
        _uiState.value = _uiState.value.copy(
            minThreshold = value,
            thresholdError = null,
            minThresholdError = null,
            successMessage = null
        )
    }

    fun onMaxThresholdChange(value: String) {
        _uiState.value = _uiState.value.copy(
            maxThreshold = value,
            thresholdError = null,
            maxThresholdError = null,
            successMessage = null
        )
    }

    fun updateThresholds(deviceId: Long) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isUpdatingThresholds = true,
                thresholdError = null,
                minThresholdError = null,
                maxThresholdError = null,
                successMessage = null
            )
            try {
                var hasError = false
                
                // Validate min threshold
                val minThreshold = _uiState.value.minThreshold.toDoubleOrNull()
                if (_uiState.value.minThreshold.isBlank()) {
                    _uiState.value = _uiState.value.copy(minThresholdError = "Required")
                    hasError = true
                } else if (minThreshold == null) {
                    _uiState.value = _uiState.value.copy(minThresholdError = "Invalid number")
                    hasError = true
                }
                
                // Validate max threshold
                val maxThreshold = _uiState.value.maxThreshold.toDoubleOrNull()
                if (_uiState.value.maxThreshold.isBlank()) {
                    _uiState.value = _uiState.value.copy(maxThresholdError = "Required")
                    hasError = true
                } else if (maxThreshold == null) {
                    _uiState.value = _uiState.value.copy(maxThresholdError = "Invalid number")
                    hasError = true
                }
                
                if (hasError) {
                    _uiState.value = _uiState.value.copy(isUpdatingThresholds = false)
                    return@launch
                }

                if (minThreshold!! >= maxThreshold!!) {
                    _uiState.value = _uiState.value.copy(
                        thresholdError = "Min threshold must be less than max threshold",
                        isUpdatingThresholds = false
                    )
                    return@launch
                }

                deviceRepository.updateThresholds(deviceId, minThreshold, maxThreshold)
                
                // Reload device to get updated thresholds
                val updatedDevice = deviceRepository.getDevice(deviceId)
                
                _uiState.value = _uiState.value.copy(
                    device = updatedDevice,
                    isUpdatingThresholds = false,
                    thresholdError = null,
                    minThresholdError = null,
                    maxThresholdError = null,
                    successMessage = "Thresholds updated successfully!"
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    thresholdError = "Failed to update: ${e.message}",
                    isUpdatingThresholds = false
                )
            }
        }
    }
}
