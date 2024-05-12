package com.chacademy.android.Utils

import androidx.lifecycle.ViewModel
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.firestore
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModelIos : UserViewModel {

    private val db = Firebase.firestore

    override fun fetchUserData(userId: String, callback: (UserData) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val user = withContext(Dispatchers.IO) {
                    getUsersById(userId)
                }
                callback(user)
            } catch (e: Exception){
                println(e)
            }
        }
    }

    override fun updateStatsStatus(userId: String, statsId: String, newStatus: Boolean) {
        CoroutineScope(Dispatchers.IO).launch(Dispatchers.IO) {
            try {
                val userDocument = db.collection("users").document(userId)
                val statsDocument = userDocument.collection("Stats").document("State")

                statsDocument.update(statsId, newStatus)

            } catch (e: Exception) {
                // Handle error
                println(e)
            }
        }
    }
}

actual fun getUserViewModel() : UserViewModel = UserViewModelIos()