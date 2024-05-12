package com.chacademy.android.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chacademy.android.Utils.UserData
import com.chacademy.android.Utils.getUsersById
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class UserDataViewModelAndroid : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    private val _userData = MutableLiveData<UserData>()
    val userData: LiveData<UserData> get() = _userData

    fun setupSnapshotListener(userId: String) {

        fetchUserData(userId){  userData ->
            _userData.value = userData
        }

        val userDataDocument = db.collection("users").document(userId)
        val userStatsDocument = userDataDocument.collection("Stats").document("State")

        userDataDocument.addSnapshotListener{ userSnapshot, error->
            if (error != null) {

                Log.e("UserDataViewModel", "Error fetching stats data: $error")
                return@addSnapshotListener
            }
            if (userSnapshot != null && userSnapshot.exists()) {
                _userData.value = _userData.value?.copy(
                    avatar = userSnapshot.getString("avatar") ?: "",
                    background = userSnapshot.getString("background") ?: "",
                    phone = userSnapshot.getString("phone") ?: userId,
                    name = userSnapshot.getString("name") ?: "",
                    date = userSnapshot.getString("date") ?: "",
                    point = userSnapshot.getLong("point") ?: 0,
                    sex = userSnapshot.getLong("sex")?.toString()?.toLong() ?: 2,
                    bio = userSnapshot.getString("bio") ?: "",
                    unlockedVideos = userSnapshot.get("videoImageList") as? List<String> ?: emptyList(),
                )
            }
        }
        userStatsDocument.addSnapshotListener{ statsSnapshot, error->
            if (error != null) {

                Log.e("UserDataViewModel", "Error fetching stats data: $error")
                return@addSnapshotListener
            }
            if (statsSnapshot != null && statsSnapshot.exists()) {
                _userData.value = _userData.value?.copy(
                    Life = statsSnapshot.getBoolean("Life") ?: true,
                    Destiny = statsSnapshot.getBoolean("Destiny") ?: false,
                    Connection = statsSnapshot.getBoolean("Connection") ?: false,
                    Growth = statsSnapshot.getBoolean("Growth") ?: false,
                    Soul = statsSnapshot.getBoolean("Soul") ?: false,
                    Personality = statsSnapshot.getBoolean("Personality") ?: false,
                    Connecting = statsSnapshot.getBoolean("Connecting") ?: false,
                    Balance = statsSnapshot.getBoolean("Balance") ?: false,
                    RationalThinking = statsSnapshot.getBoolean("RationalThinking") ?: false,
                    MentalPower = statsSnapshot.getBoolean("MentalPower") ?: false,
                    DayOfBirth = statsSnapshot.getBoolean("DayOfBirth") ?: false,
                    Passion = statsSnapshot.getBoolean("Passion") ?: false,
                    MissingNumbers = statsSnapshot.getBoolean("MissingNumbers") ?: false,
                    PersonalYear = statsSnapshot.getBoolean("PersonalYear") ?: false,
                    PersonalMonth = statsSnapshot.getBoolean("PersonalMonth") ?: false,
                    PersonalDay = statsSnapshot.getBoolean("PersonalDay") ?: false,
                    Phrase = statsSnapshot.getBoolean("Phrase") ?: false,
                    Challange = statsSnapshot.getBoolean("Challange") ?: false,
                    Agging = statsSnapshot.getBoolean("Agging") ?: false
                )
            }
        }
    }

    private fun fetchUserData(userId: String, callback: (UserData) -> Unit) {
        viewModelScope.launch {
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

    fun updateStatsStatus(userId: String, statsId: String, newStatus: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val userDocument = db.collection("users").document(userId)
                val statsDocument = userDocument.collection("Stats").document("State")

                statsDocument.update(statsId, newStatus).await()

                // Fetch the updated user data after the update
                val updatedUserSnapshot = userDocument.get().await()
                val updatedStatsSnapshot = statsDocument.get().await()

                val updatedUserData = UserData(
                    // Populate other fields...
                    Life = updatedStatsSnapshot.getBoolean("Life") ?: true
                )

                // Update the LiveData with the updated user data
                _userData.value = updatedUserData
            } catch (e: Exception) {
                // Handle error
                Log.e("UserDataViewModel", "Error updating Life status: $e")
            }
        }
    }
}