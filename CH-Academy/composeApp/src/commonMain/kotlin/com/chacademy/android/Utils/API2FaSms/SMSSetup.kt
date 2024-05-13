package com.chacademy.android.Utils.API2FaSms

import kotlinx.serialization.Serializable
import com.google.gson.annotations.SerializedName
data class SMSSetup(

)


@Serializable
data class Configuration(
    @SerializedName("pinAttempts"                   ) var pinAttempts                   : Int?     = null,
    @SerializedName("allowMultiplePinVerifications" ) var allowMultiplePinVerifications : Boolean? = null,
    @SerializedName("pinTimeToLive"                 ) var pinTimeToLive                 : String?  = null,
    @SerializedName("verifyPinLimit"                ) var verifyPinLimit                : String?  = null,
    @SerializedName("sendPinPerApplicationLimit"    ) var sendPinPerApplicationLimit    : String?  = null,
    @SerializedName("sendPinPerPhoneNumberLimit"    ) var sendPinPerPhoneNumberLimit    : String?  = null
)