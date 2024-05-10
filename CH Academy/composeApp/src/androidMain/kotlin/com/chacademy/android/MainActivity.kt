package com.chacademy.android

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.chacademy.android.Utils.UserViewModel
import com.google.firebase.Firebase
import com.google.firebase.initialize
import dev.icerock.moko.mvvm.viewmodel.ViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Firebase.initialize(this)

//        val androidCipher = AndroidCipherWrapper()
//        val firestoreRepository = AndroidFirestoreRepository()
//        val userViewModel = UserViewModel(firestoreRepository)
        setContent {
            App()
        }
    }
}