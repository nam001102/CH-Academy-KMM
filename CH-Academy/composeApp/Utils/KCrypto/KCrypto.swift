import Foundation
import CommonCrypto


@objc public class KCrypto : NSObject {
    @objc(encrypt: ) public class func encrypt(strToEncrypt: String) -> String? {
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
            let cipherKey = key.base64EncodedString()
            let cipherIv = iv.base64EncodedString()

            return "{\(cipherIv),\(cipherKey),\(cipherText)}"
        } else {
            print("Encryption failed")
            return nil
        }
    }

    @objc(decrypt: ) public class func decrypt(pass: String) -> String? {
        let passwordArr = pass.components(separatedBy: ",")

        guard passwordArr.count == 3,
              let iv = Data(base64Encoded: passwordArr[0]),
              let key = Data(base64Encoded: passwordArr[1]),
              let encryptedData = Data(base64Encoded: passwordArr[2])
        else {
            return nil
        }

        var buffer = [UInt8](repeating: 0, count: encryptedData.count + kCCBlockSizeAES128)
        var bufferLen: Int = 0

        let status = encryptedData.withUnsafeBytes { encryptedBytes in
            iv.withUnsafeBytes { ivBytes in
                key.withUnsafeBytes { keyBytes in
                    CCCrypt(
                        CCOperation(kCCDecrypt),
                        CCAlgorithm(kCCAlgorithmAES),
                        CCOptions(kCCOptionPKCS7Padding),
                        keyBytes.baseAddress,
                        key.count,
                        ivBytes.baseAddress,
                        encryptedBytes.baseAddress,
                        encryptedData.count,
                        &buffer,
                        buffer.count,
                        &bufferLen
                    )
                }
            }
        }

        if status == kCCSuccess {
            let decryptedData = Data(bytes: buffer, count: bufferLen)
            if let decryptedPassword = String(data: decryptedData, encoding: .utf8) {
                return decryptedPassword
            } else {
                print("Decoding failed")
                return nil
            }
        } else {
            print("Decryption failed")
            return nil
        }
    }

}
