package com.example.waterlevelmonitoring.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waterlevelmonitoring.data.AuthTokenHolder
import com.example.waterlevelmonitoring.data.model.AuthRequest
import com.example.waterlevelmonitoring.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// 1. Centralized UI State data class with new fields
data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null, // For general login errors
    val usernameError: String? = null, // For username field validation
    val passwordError: String? = null, // For password field validation
    val isPasswordVisible: Boolean = false
)

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {

    // 2. Use a single StateFlow to manage the UI state
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onUsernameChange(newUsername: String) {
        _uiState.value = _uiState.value.copy(username = newUsername, usernameError = null)
    }

    fun onPasswordChange(newPassword: String) {
        _uiState.value = _uiState.value.copy(password = newPassword, passwordError = null)
    }

    fun onPasswordVisibilityChange(isVisible: Boolean) {
        _uiState.update { it.copy(isPasswordVisible = isVisible) }
    }

    private fun validateFields(): Boolean {
        var isValid = true
        val currentState = _uiState.value

        if (currentState.username.isBlank()) {
            _uiState.update { it.copy(usernameError = "Username cannot be empty") }
            isValid = false
        }

        if (currentState.password.isBlank()) {
            _uiState.update { it.copy(passwordError = "Password cannot be empty") }
            isValid = false
        }

        return isValid
    }

    fun login(onLoginSuccess: () -> Unit) {
        if (!validateFields()) {
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val currentState = _uiState.value
                val authResponse = authRepository.login(AuthRequest(currentState.username, currentState.password))
                AuthTokenHolder.token = authResponse.token
                onLoginSuccess()
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }
}
