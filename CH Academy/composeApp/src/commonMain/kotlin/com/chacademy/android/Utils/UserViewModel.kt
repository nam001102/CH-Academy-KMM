package com.chacademy.android.Utils

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable

interface FirestoreRepository {
    val userData: LiveData<UserData>
    fun setupSnapshotListener(userId: String)
}

@Serializable
data class UserData(
    val admin: Long = 0,
    var avatar: String? = "",
    var background: String? = "",
    var phone: String? = null,
    var name: String? = null,
    var date: String? = null,
    var email: String? = null,
    var emailZoom: String? = null,
    var point: Long? = 0,
    var sex: Long? = 2,
    var bio: String? = "",
    var unlockedVideos: List<String>? = emptyList(),
    var Life: Boolean = true,
    var Destiny: Boolean = true,
    var Connection: Boolean = false,
    var Growth: Boolean = false,
    var Soul: Boolean = false,
    var Personality: Boolean = false,
    var Connecting: Boolean = false,
    var Balance: Boolean = false,
    var RationalThinking: Boolean = false,
    var MentalPower: Boolean = false,
    var DayOfBirth: Boolean = false,
    var Passion: Boolean = false,
    var MissingNumbers: Boolean = false,
    var PersonalYear: Boolean = false,
    var PersonalMonth: Boolean = false,
    var PersonalDay: Boolean = false,
    var Phrase: Boolean = false,
    var Challange: Boolean = false,
    var Agging: Boolean = false
)
