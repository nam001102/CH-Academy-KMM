package com.chacademy.android.Login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val message: String) : AuthState()
    data class Error(val error: String) : AuthState()
    data class OTPRequested(val verificationId: String) : AuthState()
}

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    fun login(email: String, password: String) {
        _authState.value = AuthState.Loading
        // Perform login logic here (similar to requestOTP/verifyOTP)
    }

    fun requestOTP(phoneNumber: String) {
        _authState.value = AuthState.Loading
        CoroutineScope(Dispatchers.Main).launch {
            authRepository.requestOTP(phoneNumber, { verificationId ->
                _authState.value = AuthState.OTPRequested(verificationId)
            }, { exception ->
                _authState.value = AuthState.Error(exception.message ?: "Unknown error")
            })
        }
    }

    fun verifyOTP(verificationId: String, code: String) {
        _authState.value = AuthState.Loading
        CoroutineScope(Dispatchers.Main).launch {
            authRepository.verifyOTP(verificationId, code, {
                _authState.value = AuthState.Success("OTP verified successfully")
            }, { exception ->
                _authState.value = AuthState.Error(exception.message ?: "Unknown error")
            })
        }
    }

    fun resendOTP(phoneNumber: String) {
        _authState.value = AuthState.Loading
        CoroutineScope(Dispatchers.Main).launch {
            authRepository.resendOTP(phoneNumber, { verificationId ->
                _authState.value = AuthState.OTPRequested(verificationId)
            }, { exception ->
                _authState.value = AuthState.Error(exception.message ?: "Unknown error")
            })
        }
    }
}

