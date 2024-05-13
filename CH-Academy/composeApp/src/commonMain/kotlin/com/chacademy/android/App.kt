package com.chacademy.android

import com.chacademy.android.Login.AccountManager
import com.chacademy.android.Login.LoginState
import com.chacademy.android.Login.LoginViewmodel
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import cafe.adriel.voyager.navigator.Navigator
import com.chacademy.android.model.UserViewmodel
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.ui.tooling.preview.Preview


@OptIn(ExperimentalResourceApi::class)
@Composable
@Preview
fun App(loginModel: UserViewmodel, loginState: LoginState) {
    MaterialTheme {
        Navigator(
            screen = AccountManager(loginModel,loginState)
        )
    }
}

