package com.chacademy.android

import com.chacademy.objclibs.kcrypto.KCrypto
import kotlinx.cinterop.ExperimentalForeignApi

class IosCipher : CipherWrapper {
    @OptIn(ExperimentalForeignApi::class)
    override fun encrypt(str: String): String {
        return Crypto.encrypt(str)!!
    }
    override fun decrypt(str: String): String {
        return Crypto.decrypt(str)!!
    }
}

object Crypto {
    @OptIn(ExperimentalForeignApi::class)
    fun encrypt(strToEncrypt: String): String? {
        return KCrypto.encrypt(strToEncrypt = strToEncrypt)
    }
    @OptIn(ExperimentalForeignApi::class)
    fun decrypt(strTodDecrypt: String): String? {
        return KCrypto.decrypt(pass = strTodDecrypt)
    }
}


actual fun getCipher(): CipherWrapper = IosCipher()
