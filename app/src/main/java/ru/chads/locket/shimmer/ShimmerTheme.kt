package ru.chads.locket.shimmer

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class ShimmerTheme(

    /**
     * The [AnimationSpec] which will be used for the traversal. Use an infinite spec to repeat
     * shimmering.
     *
     * @see defaultShimmerTheme
     */
    val animationSpec: AnimationSpec<Float>,

    /**
     * The [BlendMode] used in the shimmer's [androidx.compose.ui.graphics.Paint]. Have a look at
     * the theming samples to get an idea on how to utilize the blend mode.
     */
    val blendMode: BlendMode,

    /**
     * Describes the orientation of the shimmer in degrees. Zero is thereby defined as shimmer
     * traversing from the left to the right. The rotation is applied clockwise.
     * Only values >= 0 will be accepted.
     */
    val rotation: Float,

    /**
     * The [shaderColors] can be used to control the colors and alpha values of the shimmer. The
     * size of the list has to be kept in sync with the [shaderColorStops]. Consult the docs of the
     * [androidx.compose.ui.graphics.LinearGradientShader] for more information and have a look
     * at the theming samples.
     *
     */
    val shaderColors: ShaderColors,

    /**
     * The [shaderColorsDark] can be used to dark theme
     *
     */
    val shaderColorsDark: ShaderColors,

    /**
     * Controls the width used to distribute the [shaderColors].
     */
    val shimmerWidth: Dp,
) {

    data class ShaderColors(
        val colors: List<Color>,
        val colorStops: List<Float>? = null,
    )

}

private val ShimmerColor = Color(0xFF05132E)

val defaultShimmerTheme: ShimmerTheme = ShimmerTheme(
    animationSpec = infiniteRepeatable(
        animation = tween(
            durationMillis = 1_600,
            easing = LinearEasing,
            delayMillis = 1,
        ),
        repeatMode = RepeatMode.Restart,
    ),
    blendMode = BlendMode.Hardlight,
    rotation = 0f,
    shaderColors = ShimmerTheme.ShaderColors(
        colors = listOf(
            ShimmerColor.copy(alpha = 0f),
            ShimmerColor.copy(alpha = .04f),
            ShimmerColor.copy(alpha = 0f),
        ),
    ),
    shaderColorsDark = ShimmerTheme.ShaderColors(
        colors = listOf(
            ShimmerColor.copy(alpha = 0f),
            ShimmerColor.copy(alpha = .08f),
            ShimmerColor.copy(alpha = 0f),
        ),
    ),
    shimmerWidth = 60.dp,
)

val LocalShimmerTheme = staticCompositionLocalOf { defaultShimmerTheme }

