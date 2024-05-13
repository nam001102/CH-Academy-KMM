@file:OptIn(org.jetbrains.compose.resources.InternalResourceApi::class)

package chacademy.composeapp.generated.resources

import kotlin.OptIn
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.FontResource

@ExperimentalResourceApi
private object Font0 {
  public val worksans_regular: FontResource by 
      lazy { init_worksans_regular() }
}

@ExperimentalResourceApi
internal val Res.font.worksans_regular: FontResource
  get() = Font0.worksans_regular

@ExperimentalResourceApi
private fun init_worksans_regular(): FontResource = org.jetbrains.compose.resources.FontResource(
  "font:worksans_regular",
    setOf(
      org.jetbrains.compose.resources.ResourceItem(setOf(), "font/worksans_regular.ttf"),
    )
)
