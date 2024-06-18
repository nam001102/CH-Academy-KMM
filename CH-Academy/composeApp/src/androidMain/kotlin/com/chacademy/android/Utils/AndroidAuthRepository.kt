package com.chacademy.android.Utils

import android.app.Activity
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.chacademy.android.Login.AuthRepository
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.util.concurrent.TimeUnit

class AndroidAuthRepository : AuthRepository {


    private var resendToken: PhoneAuthProvider.ForceResendingToken? = null

    override fun requestOTP(phoneNumber: String, onSuccess: (verificationId: String) -> Unit, onError: (exception: Exception) -> Unit) {

        val auth = Firebase.auth

        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {

            }

            override fun onVerificationFailed(e: FirebaseException) {
                onError(e)
            }

            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                resendToken = token
                onSuccess(verificationId)
            }
        }

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this as Activity)                 // Activity (for callback binding)
            .setCallbacks(callbacks)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)

    }

    override fun verifyOTP(verificationId: String, code: String, onSuccess: () -> Unit, onError: (exception: Exception) -> Unit) {
        val credential = PhoneAuthProvider.getCredential(verificationId, code)
        val auth = FirebaseAuth.getInstance()

        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onSuccess()
            } else {
                onError(task.exception ?: Exception("Verification failed"))
            }
        }
    }

    override fun resendOTP(
        phoneNumber: String,
        onSuccess: (verificationId: String) -> Unit,
        onError: (exception: Exception) -> Unit
    ) {
        val auth = FirebaseAuth.getInstance()

        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // Auto-retrieval or instant verification completed
                // This method will be called if the code is automatically detected
                // You can proceed to sign in with the credential
            }

            override fun onVerificationFailed(e: FirebaseException) {
                // Verification failed
                onError(e)
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                // Code successfully sent to phoneNumber
                // Save verification ID and resending token
                resendToken = token
                onSuccess(verificationId)
            }
        }

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(Activity()) // Activity (for callback binding)
            .setCallbacks(callbacks) // Callbacks to handle verification events
            .setForceResendingToken(resendToken!!) // Specify the token to trigger resend
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }
}
