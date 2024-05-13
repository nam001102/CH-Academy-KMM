package com.chacademy.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.chacademy.android.Login.AuthViewModel
import com.chacademy.android.Utils.AndroidAuthRepository
import com.chacademy.android.Utils.AuthViewModelFactory
import com.chacademy.android.Utils.aesDecrypt
import com.chacademy.android.model.UserViewmodel
import com.google.firebase.Firebase
import com.google.firebase.initialize
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val authViewModel: AuthViewModel by viewModels {
        AuthViewModelFactory(AndroidAuthRepository())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Firebase.initialize(this)

        val userViewmodel: UserViewmodel by viewModels()



        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                userViewmodel.userState.collect {userState ->
                    setContent {
                        App(userViewmodel, userState)

                    }
                }
            }
        }
    }
}