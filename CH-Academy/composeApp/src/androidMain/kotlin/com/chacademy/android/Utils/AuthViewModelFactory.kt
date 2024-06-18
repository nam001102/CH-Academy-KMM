package com.chacademy.android.Utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chacademy.android.Login.AuthRepository
import com.chacademy.android.Login.AuthViewModel

class AuthViewModelFactory(private val authRepository: AuthRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(authRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
