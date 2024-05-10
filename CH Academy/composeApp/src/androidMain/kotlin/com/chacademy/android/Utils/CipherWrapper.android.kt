package com.chacademy.android.Utils

import android.os.Build
import androidx.annotation.RequiresApi

class AndroidCipher : CipherWrapper {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun encrypt(str: String): String {
        return aesEncrypt(str)!!
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun decrypt(str: String): String {
        return aesDecrypt(str)!!
    }
}


actual fun getCipher(): CipherWrapper = AndroidCipher()