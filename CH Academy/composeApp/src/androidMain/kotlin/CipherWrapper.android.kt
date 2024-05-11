import android.os.Build
import androidx.annotation.RequiresApi
import com.chacademy.android.Utils.aesDecrypt
import com.chacademy.android.Utils.aesEncrypt

class AndroidCipher : CipherWrapper {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun encrypt(str: String): String {
        return aesEncrypt(str)!!
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun decrypt(str: String): String {
        return aesDecrypt(str)!!
    }
}


actual fun getCipher(): CipherWrapper = AndroidCipher()