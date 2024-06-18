package com.chacademy.android.Login

interface AuthRepository {
    fun requestOTP(phoneNumber: String, onSuccess: (verificationId: String) -> Unit, onError: (exception: Exception) -> Unit)
    fun verifyOTP(verificationId: String, code: String, onSuccess: () -> Unit, onError: (exception: Exception) -> Unit)
    fun resendOTP(phoneNumber: String, onSuccess: (verificationId: String) -> Unit, onError: (exception: Exception) -> Unit)
}