package com.chacademy.android.Utils

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore

import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.viewmodel.ViewModel

//class AndroidFirestoreRepository : FirestoreRepository {
//    private val db = Firebase.firestore
//
//    private val _userData = MutableLiveData<UserData>(UserData())
//    override val userData: LiveData<UserData> get() = _userData
//
//    override suspend fun setupSnapshotListener(userId: String) {
//        val userDataDocument = db.collection("users").document(userId)
//        val userStatsDocument = userDataDocument.collection("Stats").document("State")
//
//        userDataDocument.snapshots.collect { document ->
//            if (document.exists) {
//                _userData.value = _userData.value.copy(
//                    avatar = document.get("avatar") ?: "",
//                    background = document.get("background") ?: "",
//                    phone = document.get("phone") ?: userId,
//                    name = document.get("name") ?: "",
//                    date = document.get("date") ?: "",
//                    point = document.get("point") ?: 0,
//                    sex = document.get("sex") ?: 2,
//                    bio = document.get("bio") ?: "",
//                    unlockedVideos = document.get("videoImageList") as? List<String>
//                        ?: emptyList(),
//                )
//            }
//
//        }
//        userStatsDocument.snapshots.collect { document ->
//            if (document.exists) {
//                _userData.value = _userData.value.copy(
//                    Life = document.get("Life") ?: true,
//                    Destiny = document.get("Destiny") ?: false,
//                    Connection = document.get("Connection") ?: false,
//                    Growth = document.get("Growth") ?: false,
//                    Soul = document.get("Soul") ?: false,
//                    Personality = document.get("Personality") ?: false,
//                    Connecting = document.get("Connecting") ?: false,
//                    Balance = document.get("Balance") ?: false,
//                    RationalThinking = document.get("RationalThinking") ?: false,
//                    MentalPower = document.get("MentalPower") ?: false,
//                    DayOfBirth = document.get("DayOfBirth") ?: false,
//                    Passion = document.get("Passion") ?: false,
//                    MissingNumbers = document.get("MissingNumbers") ?: false,
//                    PersonalYear = document.get("PersonalYear") ?: false,
//                    PersonalMonth = document.get("PersonalMonth") ?: false,
//                    PersonalDay = document.get("PersonalDay") ?: false,
//                    Phrase = document.get("Phrase") ?: false,
//                    Challange = document.get("Challange") ?: false,
//                    Agging = document.get("Agging") ?: false
//                )
//            }
//        }
//    }
//}