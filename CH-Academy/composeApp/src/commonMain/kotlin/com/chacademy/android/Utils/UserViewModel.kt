package com.chacademy.android.Utils

import dev.gitlive.firebase.Firebase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.gitlive.firebase.firestore.firestore
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.Serializable

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

interface UserViewModel  {

    fun fetchUserData(userId: String, callback: (UserData) -> Unit)

    fun updateStatsStatus(userId: String, statsId: String, newStatus: Boolean)

}

expect fun getUserViewModel() : UserViewModel

suspend fun getUsers(): List<UserData> {
    val firebaseFirestore = Firebase.firestore
    try {
        val userResponse =
            firebaseFirestore.collection("users").get()
        return userResponse.documents.map {
            it.data()
        }
    } catch (e: Exception) {
        throw e
    }
}

suspend fun getUsersById(userId: String): UserData {
    val db = Firebase.firestore
    val userDataDocument = db.collection("users").document(userId)
    val userStatsDocument = userDataDocument.collection("Stats").document("State").get()

    try {
        val avatar = userDataDocument.get().get("avatar") ?:""
        val background = userDataDocument.get().get("background") ?:""
        val phone = userDataDocument.get().get("phone") ?:""
        val name = userDataDocument.get().get("name") ?:""
        val date = userDataDocument.get().get("date") ?:""
        val point = userDataDocument.get().get("point") ?:0.toLong()
        val sex = userDataDocument.get().get("sex") ?:0.toLong()
        val bio = userDataDocument.get().get("bio") ?:""
        val unlockedVideos = userDataDocument.get().get("videoImageList") ?: emptyList<String>()
        val Life = userStatsDocument.get("Life") ?: true
        val Destiny = userStatsDocument.get("Destiny") ?: false
        val Connection = userStatsDocument.get("Connection") ?: false
        val Growth = userStatsDocument.get("Growth") ?: false
        val Soul = userStatsDocument.get("Soul") ?: false
        val Personality = userStatsDocument.get("Personality") ?: false
        val Connecting = userStatsDocument.get("Connecting") ?: false
        val Balance = userStatsDocument.get("Balance") ?: false
        val RationalThinking = userStatsDocument.get("RationalThinking") ?: false
        val MentalPower = userStatsDocument.get("MentalPower") ?: false
        val DayOfBirth = userStatsDocument.get("DayOfBirth") ?: false
        val Passion = userStatsDocument.get("Passion") ?: false
        val MissingNumbers = userStatsDocument.get("MissingNumbers") ?: false
        val PersonalYear = userStatsDocument.get("PersonalYear") ?: false
        val PersonalMonth = userStatsDocument.get("PersonalMonth") ?: false
        val PersonalDay = userStatsDocument.get("PersonalDay") ?: false
        val Phrase = userStatsDocument.get("Phrase") ?: false
        val Challange = userStatsDocument.get("Challange") ?: false
        val Agging = userStatsDocument.get("Agging") ?: false

        return UserData(
            avatar = avatar,
            background = background,
            phone = phone,
            name = name,
            date = date,
            point = point,
            sex = sex,
            bio = bio,
            unlockedVideos = unlockedVideos,
            Life = Life,
            Destiny = Destiny,
            Connection = Connection,
            Growth = Growth,
            Soul = Soul,
            Personality = Personality,
            Connecting = Connecting,
            Balance = Balance,
            RationalThinking = RationalThinking,
            MentalPower = MentalPower,
            DayOfBirth = DayOfBirth,
            Passion = Passion,
            MissingNumbers = MissingNumbers,
            PersonalYear = PersonalYear,
            PersonalMonth = PersonalMonth,
            PersonalDay = PersonalDay,
            Phrase = Phrase,
            Challange = Challange,
            Agging = Agging
        )

    } catch (e: Exception) {
        throw e
    }
}


