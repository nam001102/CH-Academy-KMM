package com.chacademy.android

interface CipherWrapper {
    fun encrypt(str: String): String
    fun decrypt(str: String): String
}

expect fun getCipher(): CipherWrapper

class Cipher {
    private val cipher = getCipher()

    fun encrypt(str: String) : String {
        return cipher.encrypt(str)
    }
    fun decrypt(str: String) : String {
        return cipher.decrypt(str)
    }
}

