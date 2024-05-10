package com.chacademy.android.Utils

import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.convert
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.toKString
import platform.Foundation.NSData
import platform.Foundation.NSString
import platform.Foundation.base64EncodedDataWithOptions
import platform.Foundation.dataUsingEncoding
import platform.Foundation.fromByteArray
import platform.Foundation.toNSData
import platform.Foundation.toNSString
import platform.Foundation.withBytes
import platform.Foundation.withCString
import platform.Foundation.withUnsafeBytes
import platform.Foundation.withUnsafeMutableBytes
import platform.Foundation.withUnsafeMutablePointer
import platform.Foundation.withUnsafePointer
import platform.posix.memcpy
import kotlin.random.Random

@OptIn(ExperimentalForeignApi::class)
actual fun generateRandomIV(length: Int): ByteArray {
    val randomIV = ByteArray(length)
    memScoped {
        val secureRandom = Random.Default
        secureRandom.nextBytes(randomIV)
    }
    return randomIV
}

@OptIn(ExperimentalUnsignedTypes::class)
actual fun aesEncrypt(pass: String): String? {
    val password = pass.toByteArray()
    val randomIV = generateRandomIV(16) // AES block size is 16 bytes
    val randomKey = generateRandomIV(32) // 256-bit key

    try {
        // Create an AES cipher instance
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val keySpec = SecretKeySpec(randomKey, "AES")
        val ivSpec = IvParameterSpec(randomIV)
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec)

        // Encrypt the data
        val encryptedBytes = cipher.doFinal(password)
        val encryptedData = encryptedBytes.toNSData()
        val base64EncodedData = encryptedData.base64EncodedDataWithOptions(0)
        return base64EncodedData.toNSString()?.toKString()
    } catch (e: Exception) {
        // Handle the error here
        return "An error occurred: $e"
    }
}

@OptIn(ExperimentalUnsignedTypes::class)
actual fun aesDecrypt(pass: String): String? {
    val passwordArr = pass.split(",")
    if (passwordArr.size != 3) {
        return null // Invalid input format
    }
    val encryptedData = NSData(base64EncodedString = passwordArr[2], options = 0)
    val inputIV = NSData(base64EncodedString = passwordArr[0], options = 0)
    val inputKey = NSData(base64EncodedString = passwordArr[1], options = 0)

    try {
        // Create an AES cipher instance for decryption
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val keySpec = SecretKeySpec(inputKey.toByteArray(), "AES")
        val ivSpec = IvParameterSpec(inputIV.toByteArray())
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec)

        // Decrypt the data
        val decryptedBytes = cipher.doFinal(encryptedData.toByteArray())
        return decryptedBytes.toKString(Charsets.UTF_8)
    } catch (e: Exception) {
        // Handle decryption errors
        println("Decryption failed: $e")
        return null
    }
}
