package model

import com.chacademy.android.Utils.UserData
import com.chacademy.android.Utils.UserViewModel
import com.chacademy.android.Utils.getUsersById
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import dev.gitlive.firebase.storage.await
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModelIos : UserViewModel {

    private val db = Firebase.firestore

    private val _userData = MutableStateFlow(UserData())
    override val userData: StateFlow<UserData> get() = _userData

    override fun setupSnapshotListener(userId: String)  {

        fun setupSnapshotListener(listener: UserViewModel) {
            // Call the Swift implementation of setupSnapshotListener
            listener.setupSnapshotListener(userId)
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

            }
        }
    }
}