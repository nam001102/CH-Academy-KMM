//
//  KCrypto.swift
//  iosApp
//
//  Created by Admin on 11/05/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import CryptoSwift


struct KCrypto {
    static func generateRandomIV(length: Int) -> [UInt8] {
        var randomIV = [UInt8](repeating: 0, count: length)
        _ = SecRandomCopyBytes(kSecRandomDefault, length, &randomIV)
        return randomIV
    }

    static func aesEncrypt(pass: String) -> String {
        let password: [UInt8] = Array(pass.utf8)
        let randomIV = generateRandomIV(length: AES.blockSize)
        let randomKey = generateRandomIV(length: 32) // 256-bit key

        do {
            /* AES cryptor instance */
            let aes = try AES(key: randomKey, blockMode: CBC(iv: randomIV), padding: .pkcs7)

            /* Encrypt Data */
            let inputData = Data(password)
            let encryptedBytes = try aes.encrypt(inputData.bytes)
            let encryptedData = Data(encryptedBytes)

            return "\(Data(randomIV).base64EncodedString()),\(Data(randomKey).base64EncodedString()),\(encryptedData.base64EncodedString())"
        } catch {
            // Handle the error here
            return "An error occurred: \(error)"
        }
    }

    static func aesDecrypt(pass: String) -> String? {
        let passwordArr = pass.components(separatedBy: ",")
        guard passwordArr.count == 3 else {
            return nil  // Invalid input format
        }
        let encryptedData = Data(base64Encoded: passwordArr[2])
        let inputIV = [UInt8](Data(base64Encoded: passwordArr[0]) ?? Data())
        let inputKey = [UInt8](Data(base64Encoded: passwordArr[1]) ?? Data())

        do {
            let aes = try AES(key: inputKey, blockMode: CBC(iv: inputIV), padding: .pkcs7)

            guard let encryptedData = encryptedData else {
                return nil  // Invalid input format
            }

            /* Decrypt Data */
            let decryptedBytes = try aes.decrypt(encryptedData.bytes)
            let decryptedData = Data(decryptedBytes)

            if let decryptedString = String(data: decryptedData, encoding: .utf8) {
                return decryptedString
            } else {
                // Handle the case where the decrypted data cannot be converted to a string
                return nil
            }
        } catch {
            // Handle decryption errors
            print("Decryption failed: \(error)")
            return nil
        }
    }

}



