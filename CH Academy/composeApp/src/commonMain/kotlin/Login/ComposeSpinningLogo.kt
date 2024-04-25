import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import chacademy.composeapp.generated.resources.Res
import chacademy.composeapp.generated.resources.ic_logo_inner
import chacademy.composeapp.generated.resources.ic_logo_outer
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@Composable
fun ComposeSpinningLogo(
    sizeTime: Float = 1f
){
    Box(
        modifier = Modifier.size((75*sizeTime).toInt().dp).clip(CircleShape),
        contentAlignment = Alignment.Center
    ){
        innerLogo(sizeTime)
        outerLogo(sizeTime)
    }

}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun innerLogo(
    size: Float = 1f
){
    Image(
        painter = painterResource(Res.drawable.ic_logo_inner),
        "image",
        Modifier
            .width((68*size).toInt().dp)
            .height((86*size).toInt().dp)
            .padding(end = 2.dp),
        contentScale = ContentScale.Fit
    )
}
@OptIn(ExperimentalResourceApi::class)
@Composable
private fun outerLogo(
    size: Float = 1f
){
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 360F,
        animationSpec = infiniteRepeatable(
            animation = tween(6000, easing = LinearEasing)
        ), label = ""
    )
    Image(
        painter = painterResource(Res.drawable.ic_logo_outer),
        "image",
        Modifier
            .width((75*size).toInt().dp)
            .height((75*size).toInt().dp)
            .graphicsLayer {
                rotationZ = angle
            },
        contentScale = ContentScale.Fit
    )
}
