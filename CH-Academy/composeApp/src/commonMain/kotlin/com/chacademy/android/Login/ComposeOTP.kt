package Login

import Cipher
import ComposeSpinningLogo
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import chacademy.composeapp.generated.resources.Res
import chacademy.composeapp.generated.resources.worksans_regular
import com.chacademy.android.Login.OtpTextField
import com.chacademy.android.Utils.AppContext
import com.chacademy.android.Utils.SPref
import com.chacademy.android.Utils.getUsersById
//import com.chacademy.android.Utils.setString
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseException
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.FirebaseAuthInvalidCredentialsException
import dev.gitlive.firebase.auth.PhoneAuthCredential
import dev.gitlive.firebase.auth.PhoneAuthProvider
import dev.gitlive.firebase.auth.PhoneVerificationProvider
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.Font

private var mforceResendingToken: PhoneAuthProvider = TODO()
private var verificationId: String = ""
private var countDownTimer: CountDownTimer? = null
private var auth: FirebaseAuth = Firebase.auth

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ComposeOTP(
    phoneNumber: String,
    Password: String,
    Name: String,
    Date: String
) {

    val user = Firebase.auth.currentUser

    if (user != null) {
        // User is signed in, proceed with SMS verification code request
        Firebase.auth.signOut()
    }

    val phoneNumberIncludeCode = "+84${phoneNumber.substring(1)}"
    val content = "Chúng tôi đã gửi mã OTP tới số điện thoại $phoneNumber"
    val context = AppContext
    var onCDSec by remember { mutableLongStateOf(0L) }
    var showError by remember { mutableStateOf(false) }
    var textError by remember {
        mutableStateOf("")
    }
    var otpValue by remember {
        mutableStateOf("")
    }
//    AppContent()
    startPhoneNumberVerification(
        phoneNumber =  phoneNumberIncludeCode,
        password =  Password,
        name =  Name,
        date =  Date
    )

    startCountdownTimer(60) { remainingSeconds ->
        onCDSec = remainingSeconds
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(start = 10.dp, end = 10.dp, top = 50.dp)
        ){
            Box(
                Modifier
                    .fillMaxWidth(),
                Alignment.Center
            ) {
                ComposeSpinningLogo()
            }
            Spacer(modifier = Modifier.padding(top = 60.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Text(
                    text = content,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(Res.font.worksans_regular)),
                    color = Color.Black
                )
            }
            Spacer(modifier = Modifier.padding(top = 20.dp))
            OtpTextField(
                otpText = otpValue,
                onOtpTextChange = { value, otpInputFilled ->
                    otpValue = value
                },
                modifier = Modifier.fillMaxWidth()
            )
            Box(
                Modifier
                    .fillMaxWidth(),
                Alignment.CenterEnd
            ) {
                Button(
                    onClick = {
                        if (onCDSec==0L){
                            startCountdownTimer(60) { remainingSeconds ->
                                onCDSec = remainingSeconds
                            }
                            onClickReSendOtp(
                                context =  context,
                                phoneNumber = phoneNumberIncludeCode,
                                Password =  Password,
                                Name =  Name,
                                Date =  Date
                            )
                        }else{
                            showError = true
                            textError = "Vui lòng nhập đợi hết thời gian"
                        }
                    },
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)
                ) {
                    Text(text = "$onCDSec Gửi lại mã", color = Color.Black)
                }
            }
            Spacer(modifier = Modifier.padding(top = 20.dp))
            showErrorCompose(showError, textError)
            Button(
                onClick = {
                    if (otpValue.isEmpty()) {
                        showError = true
                        textError = "Vui lòng nhập mã OTP"
                    } else {
                        onClickSendOtp(
                            activity = activity,
                            context =  context,
                            navController = navController,
                            phoneNumber = phoneNumber,
                            strOtp = otpValue,
                            Password =  Password,
                            Name =  Name,
                            Date =  Date
                        )
                    }

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp)
                    .background(
                        color = Color.Transparent,
                        shape = MaterialTheme.shapes.small // You can adjust the shape as needed
                    ),
                border = BorderStroke(1.dp, Color.Black),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)
            ) {
                Text(
                    text = "Xác nhận",
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(Res.font.worksans_regular)),
                    color = Color(0xFF01081E)
                )
            }
        }

    }
}

private fun startCountdownTimer(seconds: Int, onTick: (Long) -> Unit) {
    object : CountDownTimer(seconds * 1000L, 1000L) {
        override fun onTick(millisUntilFinished: Long) {
            // Calculate remaining seconds and invoke the callback
            val remainingSeconds = millisUntilFinished / 1000
            onTick(remainingSeconds)
        }

        override fun onFinish() {
            // Countdown timer finished, you can perform any additional actions here
            onTick(0)
        }
    }.start()
}

private fun stopCountdownTimer() {
    countDownTimer?.cancel()
}

private fun onClickSendOtp(
    activity: Activity,
    context: Context,
    navController:NavController,
    phoneNumber: String,
    strOtp: String,
    Password: String,
    Name: String,
    Date: String
) {
    val credential = PhoneAuthProvider(auth).credential(verificationId, strOtp)
    signInWithPhoneAuthCredential(activity, navController, context, credential, phoneNumber, Password, Name, Date)
}

private fun onClickReSendOtp(
    phoneNumber: String,
    Password: String,
    Name: String,
    Date: String
) {
    // [START start_phone_auth]
    val options = mforceResendingToken?.let {
        PhoneAuthOptions.newBuilder(Firebase.auth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(activity) // Activity (for callback binding)
            .setForceResendingToken(it)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                    signInWithPhoneAuthCredential(activity, navController, context, p0, phoneNumber, Password, Name, Date)
                }

                override fun onVerificationFailed(p0: FirebaseException) {
                    Toast.makeText(context, "Lỗi Xác Nhận", Toast.LENGTH_SHORT).show()
                }

                override fun onCodeSent(
                    VerificationId: String,
                    ForceResendingToken: PhoneAuthProvider.ForceResendingToken
                ) {
                    super.onCodeSent(VerificationId, ForceResendingToken)
                    verificationId = VerificationId
                    mforceResendingToken = ForceResendingToken
                }

            })          // OnVerificationStateChangedCallbacks
            .build()
    }
    if (options != null) {
        PhoneAuthProvider.verifyPhoneNumber(options)
    }
    // [END start_phone_auth]

}

@Composable
private fun startPhoneNumberVerification(
    phoneNumber: String,
    password: String,
    name: String,
    date: String
) {
    val context = LocalContext.current
    val auth = Firebase.auth
    val callbacks = remember {
        object : PhoneAuthProvider(Firebase.auth).verifyPhoneNumber(phoneNumber, PhoneAuthProvider(auth)) {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                signInWithPhoneAuthCredential( credential, phoneNumber, password, name, date)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                // Handle verification failure
            }

            override fun onCodeSent(
                VerificationId: String,
                ForceResendingToken: PhoneAuthProvider.ForceResendingToken
            ) {
                super.onCodeSent(VerificationId, ForceResendingToken)
                verificationId = VerificationId
                mforceResendingToken = ForceResendingToken
            }
        }
    }

    val options = PhoneAuthOptions.newBuilder(auth)
        .setPhoneNumber(phoneNumber)       // Phone number to verify
        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
        .setActivity(activity)                 // Activity (for callback binding)
        .setCallbacks(callbacks)
        .build()

    PhoneAuthProvider.verifyPhoneNumber(options)
}


private suspend fun signInWithPhoneAuthCredential(
    credential: PhoneAuthCredential,
    userPhone: String,
    password: String,
    name: String,
    date: String
) {
    auth.signInWithCredential(credential)
    if (auth.currentUser != null) {
        // Sign in success, update UI with the signed-in user's information
        val sharedPreferences = SPref()
        val storedPhoneNumber = sharedPreferences.setString("user","")
        val isForgot = sharedPreferences.getBoolean("isForgot")

        if (isForgot){
//            navController.navigate("forgotScreen?phoneNumber=${auth.currentUser?.phoneNumber}")
        }else{
            CoroutineScope(Dispatchers.IO).launch {
                auth.currentUser?.phoneNumber?.let {
                    gotoMainActivity(
                        it, name, date, password)
                }
            }
        }
    }
//    else {
//        // Sign in failed, display a message and update the UI
//        if (task.exception is FirebaseAuthInvalidCredentialsException) {
//            // The verification code entered was invalid
//
//        }
//        // Update UI
//    }
}

private suspend fun gotoMainActivity(
    phoneNumber: String?,
    name: String,
    date: String,
    password: String
) {

    if (phoneNumber != null) {
        Firebase.auth
            .createUserWithEmailAndPassword("$phoneNumber@cth.coach", phoneNumber)
    }
    val db = Firebase.firestore
    val userDocRef = phoneNumber?.let { db.collection("users").document(it) }
    val statsDocRef = userDocRef?.collection("Stats")?.document("State")

    val encryptedPassword = Cipher().encrypt(password)


    val data = hashMapOf(
        "name" to name,
        "password" to encryptedPassword,
        "date" to date,
        "email" to "",
        "point" to 0,
        "sex" to 2,
        "avatar" to "",
        "background" to "",
        "bio" to "",
        "isAdmin" to false
    )
    val stats = hashMapOf(
        "Life" to true,
        "Destiny" to false,
        "Connection" to false,
        "Growth" to false,
        "Soul" to false,
        "Personality" to false,
        "Connecting" to false,
        "Balance" to false,
        "RationalThinking" to false,
        "MentalPower" to false,
        "DayOfBirth" to false,
        "Passion" to false,
        "MissingNumbers" to false,
        "PersonalYear" to false,
        "PersonalMonth" to false,
        "PersonalDay" to false,
        "Phrase" to false,
        "Challange" to false,
        "Agging" to false
    )
    userDocRef?.set(data)
    statsDocRef?.set(stats)


}

fun replaceFirstChar(input: String, replacement: String): String {
    if (input.isEmpty()) {
        return input
    }
    return replacement + input.substring(1)
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun showErrorCompose(showError: Boolean, error: String) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = showError,
            enter = slideInVertically(
                initialOffsetY = { -40 },
                animationSpec = tween(durationMillis = 250)
            ),
            exit = fadeOut(
                animationSpec = tween(durationMillis = 250)
            )
        ) {
            Text(
                text = error,
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(Res.font.worksans_regular)),
                color = Color(0xFFFB7272)
            )
        }
    }
}