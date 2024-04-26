package com.chacademy.android.Utils

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.MutableLiveData

class AndroidFirestoreRepository : FirestoreRepository {
    private val db = FirebaseFirestore.getInstance()

    private val _userData = MutableLiveData<UserData>(UserData())
    override val userData: LiveData<UserData> get() = _userData

    override fun setupSnapshotListener(userId: String) {
        val userDataDocument = db.collection("users").document(userId)
        val userStatsDocument = userDataDocument.collection("Stats").document("State")

        userDataDocument.addSnapshotListener { userSnapshot, error ->
            if (error != null) {

                Log.e("UserDataViewModel", "Error fetching stats data: $error")
                return@addSnapshotListener
            }
            if (userSnapshot != null && userSnapshot.exists()) {
                _userData.value = _userData.value.copy(
                    avatar = userSnapshot.getString("avatar") ?: "",
                    background = userSnapshot.getString("background") ?: "",
                    phone = userSnapshot.getString("phone") ?: userId,
                    name = userSnapshot.getString("name") ?: "",
                    date = userSnapshot.getString("date") ?: "",
                    point = userSnapshot.getLong("point") ?: 0,
                    sex = userSnapshot.getLong("sex")?.toString()?.toLong() ?: 2,
                    bio = userSnapshot.getString("bio") ?: "",
                    unlockedVideos = userSnapshot.get("videoImageList") as? List<String>
                        ?: emptyList(),
                )
            }
        }

        userStatsDocument.addSnapshotListener { statsSnapshot, error ->
            if (error != null) {

                Log.e("UserDataViewModel", "Error fetching stats data: $error")
                return@addSnapshotListener
            }
            if (statsSnapshot != null && statsSnapshot.exists()) {
                _userData.value = _userData.value.copy(
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
}