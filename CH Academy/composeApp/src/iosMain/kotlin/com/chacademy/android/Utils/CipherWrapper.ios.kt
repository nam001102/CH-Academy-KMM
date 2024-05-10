package com.chacademy.android.Utils

class IosCipher : CipherWrapper {
    override fun encrypt(str: String): String {
        return aes2.aesEncrypt(str)
    }
    override fun decrypt(str: String): String {
        return aesDecrypt(str)
    }
}


actual fun getCipher(): CipherWrapper = IosCipher()