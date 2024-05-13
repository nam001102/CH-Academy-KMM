package com.chacademy.android

import android.os.Build
import androidx.annotation.RequiresApi
import com.chacademy.android.Utils.aesDecrypt
import com.chacademy.android.Utils.aesEncrypt

class AndroidCipher : CipherWrapper {
    override fun encrypt(str: String): String {
        return aesEncrypt(str)!!
    }
    override fun decrypt(str: String): String {
        return aesDecrypt(str)!!
    }
}


actual fun getCipher(): CipherWrapper = AndroidCipher()