package Login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import com.chacademy.android.Utils.AppContext
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import chacademy.composeapp.generated.resources.Res
import chacademy.composeapp.generated.resources.ic_invisable
import chacademy.composeapp.generated.resources.ic_visable
import chacademy.composeapp.generated.resources.worksans_regular
import Cipher
import com.chacademy.android.Utils.UserData
import com.chacademy.android.Utils.getUserViewModel
import com.chacademy.android.Utils.getUsersById
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import getPlatform
import kotlinx.coroutines.flow.MutableStateFlow
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource


private var userPhone by mutableStateOf(TextFieldValue(""))
private var userPassword by mutableStateOf(TextFieldValue(""))
private var errorMessage = ""
private val context = AppContext
private val auth = Firebase.auth

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ComposeLogin() {

    var platform = if (getPlatform().shortname.equals("Android")) 1 else 0

    val stateFlow: MutableStateFlow<UserData> = MutableStateFlow(UserData())


//    when(platform){
//        1 -> {
//            // Call setupSnapshotListener for Android
//            observeStateFlow(getUserViewModel())
//        }
//        0 -> {
//            // Call setupSnapshotListener for iOS
//            observeStateFlow(getUserViewModel())
//        }
//    }

    val interactionSource = remember { MutableInteractionSource() }
    var isForgot by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    var textError by remember {
        mutableStateOf("")
    }

    val userDataState = remember { mutableStateOf<UserData?>(null) }
    var userId = remember { mutableStateOf("") }
    var list by remember { mutableStateOf(UserData()) }
    LaunchedEffect(Unit) {
//            userViewModel.getUserData(userId.value)
        list = getUsersById("+84987654322")

        getUserViewModel().fetchUserData("+84987654322"){ userData ->
            stateFlow.value = userData
        }

    }





    var loggingIn = false

//    if (loggingIn){
//
//        LaunchedEffect(loggingIn){
//            startLoggingIn(userPhone.text, userPassword.text)
//        }
//    }

    showError = true
    errorMessage = list.name.toString()
//    errorMessage = list.point.toString()



    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = Color.White)
    ) {
        Spacer(modifier = Modifier.padding(top = 10.dp))
        Text(
            text = "Đăng nhập",
            fontSize = 26.sp,
            fontFamily = FontFamily(Font(Res.font.worksans_regular)),
            color = Color.Black
        )
        loginInput()
        Box(
            Modifier
                .fillMaxWidth(),
            Alignment.CenterEnd
        ) {
            Button(
                onClick = {
                    if (userPhone.text.isEmpty()) {
                        showError = true
                        textError = "Vui lòng nhập số điện thoại"
                    } else {
                        userId.value = "+84${userPhone.text.substring(1)}"
                        val db = Firebase.firestore

                        isForgot = true

//                        val userDocRef =  db.collection("users").document(userId.value).get()

//                        userDocRef.get().addOnSuccessListener { document ->
//                            if (document != null && document.exists()) {
//                                // if the document exists, get the user's data as a custom data class
//                                val name = document.getString("name")
//                                val date = document.getString("date")
//                                if (name != null) {
//                                    if (date != null) {
//                                        val sharedPreferences = context.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
//                                        val editor = sharedPreferences.edit()
//                                        editor.putBoolean("isForgot", isForgot)
//                                        editor.apply()
//                                        navController.navigate("otpScreen?Date=${date}&Name=${name}&Password=${userPassword.text}&phoneNumber=${userPhone.text}")
//                                    }
//                                }
//                            }
//                        }
                    }
                },
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)
            ) {
                Text(
                    text = "Quên mật khẩu?",
                    color = Color.Black,
                    modifier = Modifier
                )
            }
        }
        if (errorMessage.isNotEmpty()) {
            showErrorCompose(showError, errorMessage)
        } else {
            showErrorCompose(showError, textError)
        }

        Spacer(modifier = Modifier.padding(top = 10.dp))
        Button(
            onClick = {
                if (userPhone.text.isEmpty()) {
                    showError = true
                    textError = "Số điện thoại không được để trống"
                } else if (userPhone.text.length != 10 || userPhone.text.take(1) != "0") {
                    showError = true
                    textError = "Số điện thoại không hợp lệ"
                } else if (userPassword.text.isEmpty()) {
                    showError = true
                    textError = "Mật khẩu không được để trống"
                } else {
                    loggingIn = !loggingIn
                }
//                if (userPhone.text.isNotEmpty() && userPassword.text.isNotEmpty()) {
//
//                }
            },
            modifier = Modifier
                .indication(
                    interactionSource = interactionSource,
                    indication = null
                )
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
                text = "Đăng nhập",
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(Res.font.worksans_regular)),
                color = Color.Black
            )
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun loginInput() {
    TextField(
        value = userPhone,
        onValueChange = {
            if (it.text.length <= 10) {
                userPhone = it
            }
        },
        colors = TextFieldDefaults.colors(
            disabledTextColor = Color.Black,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            cursorColor = Color.Black,
            focusedIndicatorColor = Color.Black,
            unfocusedIndicatorColor = Color.Black,
            disabledLabelColor = Color.Black,
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next
        ),
        visualTransformation = VisualTransformation.None,
        textStyle = TextStyle(
            fontFamily = FontFamily(Font(Res.font.worksans_regular)), // Set your desired font family
            fontSize = 22.sp,
            color = Color(0xFF01081E)
        ),
        singleLine = true,
        modifier = Modifier
            .height(70.dp)
            .fillMaxWidth(),
        label = {
            Box {
                Text(
                    text = "Số điện thoại",
                    color = Color.Black, // Set the label text color
                    modifier = Modifier.padding(top = 4.dp),
                    style = TextStyle(
                        fontFamily = FontFamily(Font(Res.font.worksans_regular)), // Set your custom font family here
                        fontSize = 16.sp,
                        letterSpacing = 0.sp,
                        lineHeight = 18.sp
                    )
                )
            }
        }
    )
    var isPasswordVisible by remember { mutableStateOf(false) }
    val icVisible = painterResource(Res.drawable.ic_visable)
    val icInvisible = painterResource(Res.drawable.ic_invisable)
    TextField(
        value = userPassword,
        onValueChange = {
            if (it.text.length <= 20) {
                userPassword = it
            }
        },
        colors = TextFieldDefaults.colors(
            disabledTextColor = Color.Black,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            cursorColor = Color.Black,
            focusedIndicatorColor = Color.Black,
            unfocusedIndicatorColor = Color.Black,
            disabledLabelColor = Color.Black,
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        visualTransformation = if (isPasswordVisible) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        textStyle = TextStyle(
            fontFamily = FontFamily(Font(Res.font.worksans_regular)), // Set your desired font family
            fontSize = 22.sp,
            color = Color(0xFF01081E)
        ),
        singleLine = true,
        modifier = Modifier
            .height(70.dp)
            .fillMaxWidth(),
        label = {
            Box {
                Text(
                    text = "Mật khẩu",
                    color = Color.Black, // Set the label text color
                    modifier = Modifier.padding(top = 4.dp),
                    style = TextStyle(
                        fontFamily = FontFamily(Font(Res.font.worksans_regular)), // Set your custom font family here
                        fontSize = 16.sp,
                        letterSpacing = 0.sp,
                        lineHeight = 18.sp
                    )
                )
            }
        },
        trailingIcon = {
            val clickableIcon = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        textDecoration = TextDecoration.None,
                        color = Color.Blue
                    )
                ) {
                    append("")
                }
            }

            ClickableText(
                text = clickableIcon,
                modifier = Modifier.size(24.dp)
            ) { offset ->
                isPasswordVisible = !isPasswordVisible
            }

            // Display the visible or invisible icon based on the state
            if (isPasswordVisible) {
                Icon(
                    painter = icVisible,
                    contentDescription = "Toggle Password Visibility",
                    modifier = Modifier
                        .size(24.dp)
                        .offset(y = 12.dp)
                )
            } else {
                Icon(
                    painter = icInvisible,
                    contentDescription = "Toggle Password Visibility",
                    modifier = Modifier
                        .size(24.dp)
                        .offset(y = 12.dp),
                    tint = Color.Gray
                )
            }
        }
    )
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
                color = Color.Red
            )
        }
    }
}

private suspend fun startLoggingIn(phoneNumber: String, Password: String) {
    val db = Firebase.firestore
    val userRef = db.collection("users").document("+84" + phoneNumber.substring(1))
    userRef.get().reference.snapshots.collect { i ->
        if (i.exists) {
            val password = i.get("password") ?:""
            val name = i.get("name") ?:""

            val decrypt = Cipher().decrypt(password)
            errorMessage = name
            if (decrypt == Password) {
                auth.signInWithEmailAndPassword(
                    "+84" + phoneNumber.substring(1) + "@cth.coach",
                    "+84" + phoneNumber.substring(1)
                )
                if (auth.currentUser != null){
                    val user = auth.currentUser
                    val uid = user?.uid
                    val data = hashMapOf(
                        "uid" to uid
                    )
                    userRef.set(data,merge = true)
//                    val sharedPreferences : ShareP
//                    Navigator(
//
//                    )
                    errorMessage = "1"
                }
            } else {
//                errorMessage = "Đăng nhập thất bại, tài khoản hoặc mật khẩu sai."
            }

        }
    }
}