@file:OptIn(org.jetbrains.compose.resources.InternalResourceApi::class)

package chacademy.composeapp.generated.resources

import kotlin.OptIn
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi

@ExperimentalResourceApi
private object Drawable0 {
  public val compose_multiplatform: DrawableResource by 
      lazy { init_compose_multiplatform() }

  public val ic_invisable: DrawableResource by 
      lazy { init_ic_invisable() }

  public val ic_logo_inner: DrawableResource by 
      lazy { init_ic_logo_inner() }

  public val ic_logo_outer: DrawableResource by 
      lazy { init_ic_logo_outer() }

  public val ic_visable: DrawableResource by 
      lazy { init_ic_visable() }
}

@ExperimentalResourceApi
internal val Res.drawable.compose_multiplatform: DrawableResource
  get() = Drawable0.compose_multiplatform

@ExperimentalResourceApi
private fun init_compose_multiplatform(): DrawableResource =
    org.jetbrains.compose.resources.DrawableResource(
  "drawable:compose_multiplatform",
    setOf(
      org.jetbrains.compose.resources.ResourceItem(setOf(), "drawable/compose-multiplatform.xml"),
    )
)

@ExperimentalResourceApi
internal val Res.drawable.ic_invisable: DrawableResource
  get() = Drawable0.ic_invisable

@ExperimentalResourceApi
private fun init_ic_invisable(): DrawableResource =
    org.jetbrains.compose.resources.DrawableResource(
  "drawable:ic_invisable",
    setOf(
      org.jetbrains.compose.resources.ResourceItem(setOf(), "drawable/ic_invisable.xml"),
    )
)

@ExperimentalResourceApi
internal val Res.drawable.ic_logo_inner: DrawableResource
  get() = Drawable0.ic_logo_inner

@ExperimentalResourceApi
private fun init_ic_logo_inner(): DrawableResource =
    org.jetbrains.compose.resources.DrawableResource(
  "drawable:ic_logo_inner",
    setOf(
      org.jetbrains.compose.resources.ResourceItem(setOf(), "drawable/ic_logo_inner.png"),
    )
)

@ExperimentalResourceApi
internal val Res.drawable.ic_logo_outer: DrawableResource
  get() = Drawable0.ic_logo_outer

@ExperimentalResourceApi
private fun init_ic_logo_outer(): DrawableResource =
    org.jetbrains.compose.resources.DrawableResource(
  "drawable:ic_logo_outer",
    setOf(
      org.jetbrains.compose.resources.ResourceItem(setOf(), "drawable/ic_logo_outer.png"),
    )
)

@ExperimentalResourceApi
internal val Res.drawable.ic_visable: DrawableResource
  get() = Drawable0.ic_visable

@ExperimentalResourceApi
private fun init_ic_visable(): DrawableResource = org.jetbrains.compose.resources.DrawableResource(
  "drawable:ic_visable",
    setOf(
      org.jetbrains.compose.resources.ResourceItem(setOf(), "drawable/ic_visable.xml"),
    )
)
