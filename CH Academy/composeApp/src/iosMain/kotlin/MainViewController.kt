import androidx.compose.ui.window.ComposeUIViewController
import com.chacademy.android.Utils.IOSFirestoreRepository
import com.chacademy.android.Utils.UserViewModel

fun MainViewController(userViewModel: UserViewModel) = ComposeUIViewController {
    App(userViewModel = userViewModel)
}