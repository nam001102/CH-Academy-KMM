package com.chacademy.android.Utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlin.random.Random

fun generateRandomIV(length: Int): ByteArray {
    val randomIV = ByteArray(length)
    val secureRandom = Random.Default
    secureRandom.nextBytes(randomIV)
    return randomIV
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalEncodingApi::class)
fun aesEncrypt(pass: String): String? {
    val password =  pass.toByteArray()
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
        val encryptedData = Base64.getEncoder().encode(encryptedBytes)

        return "${Base64.getEncoder().encode(randomIV)},${Base64.getEncoder().encode(randomKey)},$encryptedData"
    } catch (e: Exception) {
        // Handle the error here
        return "An error occurred: $e"
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalEncodingApi::class)
fun aesDecrypt(pass: String): String? {
    val passwordArr = pass.split(",")
    if (passwordArr.size != 3) {
        return null // Invalid input format
    }
    val encryptedData = Base64.getDecoder().decode(passwordArr[2])
    val inputIV = Base64.getDecoder().decode(passwordArr[0])
    val inputKey = Base64.getDecoder().decode(passwordArr[1])

    try {
        // Create an AES cipher instance for decryption
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val keySpec = SecretKeySpec(inputKey, "AES")
        val ivSpec = IvParameterSpec(inputIV)
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec)

        // Decrypt the data
        val decryptedBytes = cipher.doFinal(encryptedData)
        val decryptedString = String(decryptedBytes, Charsets.UTF_8)
        return decryptedString
    } catch (e: Exception) {
        // Handle decryption errors
        println("Decryption failed: $e")
        return null
    }
}

fun decimalToAlphabet(str : String) : String{
    val digitArray = str.map { it.toString().toInt() }.toTypedArray()
    val shiftTimes = digitArray.last()
    val digitToCharMap = ('A'..'Z').toList().withIndex().associate { (index, char) -> index to char }
    val charArray = digitArray.map { digitToCharMap[it] ?: '?' }
    val result = charArray.joinToString("")

    return shiftStringForward(result, shiftTimes)
}
fun alphabetToDecimal(str : String,phone: String) : String{
    val digitArray = phone.map { it.toString().toInt() }.toTypedArray()
    val shiftTimes = digitArray.last()
    val charArray = shiftStringBackward(str, shiftTimes).toCharArray()
    val numberArray = mutableListOf<String>()
    for (char in charArray){
        numberArray.add(convertToNumeric(char))
    }

    return numberArray.toString()
}
private fun convertToNumeric(inputText: Char): String {
    val uppercaseInput = inputText.toUpperCase()
    var numericValue = 0
    if (uppercaseInput.isLetter()) {
        val numericCharValue = uppercaseInput.toInt() - 'A'.toInt()
        numericValue = numericValue * 26 + numericCharValue
    }
    return numericValue.toString()
}
private fun shiftStringForward(input: String, shiftAmount: Int): String {
    val shiftedChars = input.map { char ->
        if (char.isLetter()) {
            val base = if (char.isUpperCase()) 'A' else 'a'
            val shiftedChar = (base + (char - base + shiftAmount) % 26).toChar()
            shiftedChar
        } else {
            char // Keep non-letter characters unchanged
        }
    }
    return shiftedChars.joinToString("")
}
private fun shiftStringBackward(input: String, shiftAmount: Int): String {
    val shiftedText = StringBuilder()

    for (char in input) {
        if (char.isLetter()) {
            val isUpperCase = char.isUpperCase()
            val shiftedChar = if (isUpperCase) {
                ((char - 'A' + 26 - shiftAmount) % 26 + 'A'.toInt()).toChar()
            } else {
                ((char - 'a' + 26 - shiftAmount) % 26 + 'a'.toInt()).toChar()
            }
            shiftedText.append(shiftedChar)
        } else {
            shiftedText.append(char)
        }
    }
    return shiftedText.toString()
}