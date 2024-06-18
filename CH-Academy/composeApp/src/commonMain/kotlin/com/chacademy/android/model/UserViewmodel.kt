package com.chacademy.android.model

import androidx.lifecycle.ViewModel
import com.chacademy.android.Login.AuthState
import com.chacademy.android.Utils.UserData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class UserState(
    object isLogin : UserState()
    object isLogout : UserState()
    object isForgotPassword : UserState()
    data class userData(val userData: userData?): UserState()
)

class UserViewmodel: ViewModel() {

    private val _userState = MutableStateFlow<UserData>(UserData())
    val userState: StateFlow<UserData> = _userState.asStateFlow()
}