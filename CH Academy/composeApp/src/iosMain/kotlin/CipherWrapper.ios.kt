

class IosCipher : CipherWrapper {
    override fun encrypt(str: String): String {
        return "KCrypto.aesEncrypt(str)"
    }
    override fun decrypt(str: String): String {
        return "KCrypto.aesDecrypt(str)"
    }
}


actual fun getCipher(): CipherWrapper = IosCipher()
