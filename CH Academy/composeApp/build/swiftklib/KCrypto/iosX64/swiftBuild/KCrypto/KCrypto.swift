import Foundation
import CryptoSwift

@objc public class KCrypto : NSObject {
    @objc(aesEncrypt:) public class func aesEncrypt(pass: String) -> String {
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

    @objc(aesDecrypt:) public class func aesDecrypt(pass: String) -> String? {
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

func generateRandomIV(length: Int) -> [UInt8] {
    var randomIV = [UInt8](repeating: 0, count: length)
    _ = SecRandomCopyBytes(kSecRandomDefault, length, &randomIV)
    return randomIV
}

func aesEncrypt(pass: String) -> String {
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

func aesDecrypt(pass: String) -> String? {
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

func encryptAndSavePassword(strToEncrypt: String) -> String? {
    guard let plainTextData = strToEncrypt.data(using: .utf8) else {
        return nil
    }
    
    let keySize = kCCKeySizeAES256
    var key = Data(count: keySize)
    key.withUnsafeMutableBytes { keyBytes in
        _ = CCRandomGenerateBytes(keyBytes.baseAddress, keySize)
    }
    
    var iv = Data(count: keySize)
    iv.withUnsafeMutableBytes { ivBytes in
        _ = CCRandomGenerateBytes(ivBytes.baseAddress, keySize)
    }
    
    var buffer = [UInt8](repeating: 0, count: plainTextData.count + kCCBlockSizeAES128)
    var bufferLen: Int = 0
    
    let status = plainTextData.withUnsafeBytes { plainTextBytes in
        iv.withUnsafeBytes { ivBytes in
            key.withUnsafeBytes { keyBytes in
                CCCrypt(
                    CCOperation(kCCEncrypt),
                    CCAlgorithm(kCCAlgorithmAES),
                    CCOptions(kCCOptionPKCS7Padding),
                    keyBytes.baseAddress,
                    keySize,
                    ivBytes.baseAddress,
                    plainTextBytes.baseAddress,
                    plainTextData.count,
                    &buffer,
                    buffer.count,
                    &bufferLen
                )
            }
        }
    }
    
    if status == kCCSuccess {
        let cipherTextData = Data(bytes: buffer, count: bufferLen)
        let cipherText = cipherTextData.base64EncodedString()
        let cipherKey = iv.base64EncodedString()
        
        return "{\(cipherKey),\(key.base64EncodedString()),\(cipherText),\(cipherText)}"
    } else {
        print("Encryption failed")
        return nil
    }
}

func getDecryptedPassword(encrypted: String, key: String, iv: String) -> String? {
    guard let encryptedData = Data(base64Encoded: encrypted),
        let decryptedData = decrypt(data: encryptedData, keyStr: key, ivStr: iv),
        let decryptedPassword = String(data: decryptedData, encoding: .utf8)
    else {
        return nil
    }
    
    return decryptedPassword
}

private func decrypt(data: Data,keyStr: String, ivStr:String)  -> Data? {
    let key:Data = getSavedSecretKey(key: keyStr)!
    let iv:Data = getSavedInitializationVector(vector: ivStr)!

    var buffer = [UInt8](repeating: 0, count: data.count + kCCBlockSizeAES128)
    var bufferLen: Int = 0

    let status = CCCrypt(
        CCOperation(kCCDecrypt),
        CCAlgorithm(kCCAlgorithmAES128),
        CCOptions(kCCOptionPKCS7Padding),
        [UInt8](key),
        kCCBlockSizeAES128,
        [UInt8](iv),
        [UInt8](data),
        data.count,
        &buffer,
        buffer.count,
        &bufferLen
    )

    if(status == kCCSuccess) {
        return Data(bytes: buffer, count: bufferLen)}
    else {
        print("Decryption failed")
        return data
    }
}
private func saveSecretKey(secretKey: Data) -> String? {
    do {
        let keyData = try NSKeyedArchiver.archivedData(withRootObject: secretKey, requiringSecureCoding: false)
        return keyData.base64EncodedString()
    } catch {
        return nil
    }
}

private func getSavedSecretKey(key: String) -> Data? {
    if let data = Data(base64Encoded: key) {
        do {
            return try NSKeyedUnarchiver.unarchiveTopLevelObjectWithData(data) as? Data
        } catch {
            return nil
        }
    }
    return nil
}

private func saveInitializationVector(initializationVector: Data) -> String? {
    do {
        let ivData = try NSKeyedArchiver.archivedData(withRootObject: initializationVector, requiringSecureCoding: false)
        return ivData.base64EncodedString()
    } catch {
        return nil
    }
}

private func getSavedInitializationVector(vector: String) -> Data? {
    if let data = Data(base64Encoded: vector) {
        do {
            return try NSKeyedUnarchiver.unarchiveTopLevelObjectWithData(data) as? Data
        } catch {
            return nil
        }
    }
    return nil
}
