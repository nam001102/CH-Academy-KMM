package Login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource

private var userPhone by mutableStateOf(TextFieldValue(""))
private var userPassword by mutableStateOf(TextFieldValue(""))
private var userRePassword by mutableStateOf(TextFieldValue(""))
private var userName by mutableStateOf(TextFieldValue(""))
private var userDate by mutableStateOf(TextFieldValue(""))

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ComposeRegister(){
    var showError by remember { mutableStateOf(false) }
    var textError by remember {
        mutableStateOf("")
    }

    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = Color.White)
    ) {
        Spacer(modifier = Modifier.padding(top = 10.dp))
        Text(
            text = "Đăng ký",
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(Res.font.worksans_regular)),
            color = Color.Black
        )
        registerInput()
        Spacer(modifier = Modifier.padding(top = 10.dp))
        showErrorCompose(showError, textError)
        Spacer(modifier = Modifier.padding(top = 10.dp))
        Button(
            onClick = {
                if (userPhone.text.isEmpty()) {
                    showError = true
                    textError = "Số điện thoại không được để trống"
                } else if (userPhone.text.length != 10 || userPhone.text.take(1) != "0") {
                    showError = true
                    textError = "Số điện thoại không hợp lệ"
                } else if (userPhone.text.length == 10 || userPhone.text.take(1) == "0") {
//                    navController.navigate("otpScreen?Date=${userDate.text}&Name=${userName.text}&Password=${userPassword.text}&phoneNumber=${userPhone.text}&isForgot=${false}")
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
                text = "Đăng ký",
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(Res.font.worksans_regular)),
                color = Color.Black
            )
        }
    }
}
@OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun registerInput() {
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
    TextField(
        value = userName,
        onValueChange = {
            userName = it
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
            keyboardType = KeyboardType.Text,
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
                    text = "Họ và tên",
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

//    val calendar = Calendar.getInstance()
//    val year = calendar.get(Calendar.YEAR)
//    val month = calendar.get(Calendar.MONTH)
//    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val now: Instant = Clock.System.now()
    val thisDay = now.toLocalDateTime(TimeZone.currentSystemDefault()).dayOfMonth
    val thisMonth = now.toLocalDateTime(TimeZone.currentSystemDefault()).month
    val thisYear = now.toLocalDateTime(TimeZone.currentSystemDefault()).year


    // Decoupled snackbar host state from scaffold state for demo purposes.
    val snackState = remember { SnackbarHostState() }
    val snackScope = rememberCoroutineScope()
    SnackbarHost(hostState = snackState, Modifier)
    val openDialog = remember { mutableStateOf(true) }
// TODO demo how to read the selected date from the state.
    if (openDialog.value) {
        val datePickerState = rememberDatePickerState(
            yearRange = (thisYear-100)..(thisYear)
        )
        val confirmEnabled = remember {
            derivedStateOf { datePickerState.selectedDateMillis != null }
        }
        DatePickerDialog(
            onDismissRequest = {
                // Dismiss the dialog when the user clicks outside the dialog or on the back
                // button. If you want to disable that functionality, simply use an empty
                // onDismissRequest.
                openDialog.value = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        snackScope.launch {
                            snackState.showSnackbar(
                                "Selected date timestamp: ${datePickerState.selectedDateMillis}"
                            )
                        }
                        val formatPattern = "dd/MM/yyyy"
                        @OptIn(FormatStringsInDatetimeFormats::class)
                        val dateTimeFormat = LocalDate.Format {
                            byUnicodePattern(formatPattern)
                        }
                        userDate = TextFieldValue(dateTimeFormat.format(
                            LocalDate.parse(
                                datePickerState.selectedDateMillis?.let {
                                    Instant.fromEpochMilliseconds(
                                        it
                                    )
                                }.toString().substring(0, 10)
                            )))
                    },
                    enabled = confirmEnabled.value
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    TextField(
        value = userDate,
        onValueChange = {
            userDate = it
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
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        visualTransformation = VisualTransformation.None,
        textStyle = TextStyle(
            fontFamily = FontFamily(Font(Res.font.worksans_regular)), // Set your desired font family
            fontSize = 22.sp,
            color = Color(0xFF01081E)
        ),
        enabled = false,
        singleLine = true,
        modifier = Modifier
            .height(70.dp)
            .fillMaxWidth()
            .clickable { openDialog.value = true },
        label = {
            Box {
                Text(
                    text = "Ngày sinh",
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
            imeAction = ImeAction.Next
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

    var isRePasswordVisible by remember { mutableStateOf(false) }
    val icReVisible = painterResource(Res.drawable.ic_visable)
    val icReInvisible = painterResource(Res.drawable.ic_invisable)

    TextField(
        value = userRePassword,
        onValueChange = {
            if (it.text.length <= 20) {
                userRePassword = it
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
        visualTransformation = if (isRePasswordVisible) {
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
                    text = "Nhập lại mật khẩu",
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
                isRePasswordVisible = !isRePasswordVisible
            }

            // Display the visible or invisible icon based on the state
            if (isRePasswordVisible) {
                Icon(
                    painter = icReVisible,
                    contentDescription = "Toggle Password Visibility",
                    modifier = Modifier
                        .size(24.dp)
                        .offset(y = 12.dp)
                )
            } else {
                Icon(
                    painter = icReInvisible,
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
                color = Color(0xFFFB7272)
            )
        }
    }
}