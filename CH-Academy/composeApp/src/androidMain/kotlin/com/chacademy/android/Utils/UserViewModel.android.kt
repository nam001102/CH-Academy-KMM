package com.chacademy.android.Utils

import android.util.Log
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class UserViewModelAndroid : UserViewModel {

    private val db = Firebase.firestore


//    override fun setupSnapshotListener(userId: String, callback: (UserData) -> Unit) {
//
//

//        val userDataDocument = db.collection("users").document(userId)
//        val userStatsDocument = userDataDocument.collection("Stats").document("State")
//
//        userDataDocument.addSnapshotListener{ userSnapshot, error->
//            if (error != null) {
//
//                Log.e("UserDataViewModel", "Error fetching stats data: $error")
//                return@addSnapshotListener
//            }
//            if (userSnapshot != null && userSnapshot.exists()) {
//                _userData.value = _userData.value.copy(
//                    avatar = userSnapshot.getString("avatar") ?: "",
//                    background = userSnapshot.getString("background") ?: "",
//                    phone = userSnapshot.getString("phone") ?: userId,
//                    name = userSnapshot.getString("name") ?: "",
//                    date = userSnapshot.getString("date") ?: "",
//                    point = userSnapshot.getLong("point") ?: 0,
//                    sex = userSnapshot.getLong("sex")?.toString()?.toLong() ?: 2,
//                    bio = userSnapshot.getString("bio") ?: "",
//                    unlockedVideos = userSnapshot.get("videoImageList") as? List<String> ?: emptyList(),
//                )
//            }
//        }
//        userStatsDocument.addSnapshotListener{ statsSnapshot, error->
//            if (error != null) {
//
//                Log.e("UserDataViewModel", "Error fetching stats data: $error")
//                return@addSnapshotListener
//            }
//            if (statsSnapshot != null && statsSnapshot.exists()) {
//                _userData.value = _userData.value.copy(
//                    Life = statsSnapshot.getBoolean("Life") ?: true,
//                    Destiny = statsSnapshot.getBoolean("Destiny") ?: false,
//                    Connection = statsSnapshot.getBoolean("Connection") ?: false,
//                    Growth = statsSnapshot.getBoolean("Growth") ?: false,
//                    Soul = statsSnapshot.getBoolean("Soul") ?: false,
//                    Personality = statsSnapshot.getBoolean("Personality") ?: false,
//                    Connecting = statsSnapshot.getBoolean("Connecting") ?: false,
//                    Balance = statsSnapshot.getBoolean("Balance") ?: false,
//                    RationalThinking = statsSnapshot.getBoolean("RationalThinking") ?: false,
//                    MentalPower = statsSnapshot.getBoolean("MentalPower") ?: false,
//                    DayOfBirth = statsSnapshot.getBoolean("DayOfBirth") ?: false,
//                    Passion = statsSnapshot.getBoolean("Passion") ?: false,
//                    MissingNumbers = statsSnapshot.getBoolean("MissingNumbers") ?: false,
//                    PersonalYear = statsSnapshot.getBoolean("PersonalYear") ?: false,
//                    PersonalMonth = statsSnapshot.getBoolean("PersonalMonth") ?: false,
//                    PersonalDay = statsSnapshot.getBoolean("PersonalDay") ?: false,
//                    Phrase = statsSnapshot.getBoolean("Phrase") ?: false,
//                    Challange = statsSnapshot.getBoolean("Challange") ?: false,
//                    Agging = statsSnapshot.getBoolean("Agging") ?: false
//                )
//            }
//        }
//    }

    override fun fetchUserData(userId: String, callback: (UserData) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val user = withContext(Dispatchers.IO) {
                    getUsersById(userId)
                }
                callback(user)
            } catch (e: Exception){
                Log.e("UserDataViewModel", "Error fetching stats data: $e")
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
                Log.e("UserDataViewModel", "Error updating Life status: $e")
            }
        }
    }
}

actual fun getUserViewModel() : UserViewModel = UserViewModelAndroid()