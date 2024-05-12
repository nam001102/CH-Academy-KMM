package Login

import ComposeSpinningLogo
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import chacademy.composeapp.generated.resources.Res
import chacademy.composeapp.generated.resources.worksans_regular
import com.chacademy.android.Utils.UserViewModel
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.Font





class AccountManager() : Screen {

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {

        var isLogin by remember { mutableStateOf(true) }
        var isLoginText by remember { mutableStateOf("Đăng ký") }

        Column(
            Modifier
                .fillMaxSize()
                .background(color = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 50.dp)
            ){
                Box(
                    Modifier
                        .fillMaxWidth(),
                    Alignment.Center
                ) {
                    ComposeSpinningLogo()
                }
                Spacer(modifier = Modifier.padding(top = 10.dp))

                Box(
                    Modifier
                        .fillMaxWidth(),
                    Alignment.CenterEnd
                ) {
                    Button(
                        onClick = {
                            isLogin = !isLogin
                            isLoginText = if (isLogin) {
                                "Đăng ký"
                            } else {
                                "Đăng nhập"
                            }
                        },
                        modifier = Modifier.background(
                            color = Color.Transparent,
                            shape = MaterialTheme.shapes.small // You can adjust the shape as needed
                        ),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black),
                    ) {
                        Text(
                            text = isLoginText,
                            fontSize = 18.sp,
                            fontFamily = FontFamily(Font(Res.font.worksans_regular)),
                            color = Color.Black
                        )
                    }
                }
                AnimatedVisibility(
                    visible = isLogin,
                    enter = fadeIn(animationSpec = tween(durationMillis = 500)),
                    exit = fadeOut(animationSpec = tween(durationMillis = 200))
                ) {
                    ComposeLogin()
                }
                AnimatedVisibility(
                    visible = !isLogin,
                    enter = fadeIn(animationSpec = tween(durationMillis = 500)),
                    exit = fadeOut(animationSpec = tween(durationMillis = 200))
                ) {
                    ComposeRegister()
                }
            }

        }
    }
}



