package Login

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth

class FirebaseAuthentication {
    suspend fun loginWithEmailAndPass(
        email: String,
        password: String
    ): String {
        return try {
            val authResult = Firebase.auth.signInWithEmailAndPassword(email, password)
            println("++signInWithEmailAndPassword Success")
            authResult.user!!.displayName!!
        } catch (e: Exception) {
            println("++Login Exception: $e")
            "Exception $e"
        }
    }
}