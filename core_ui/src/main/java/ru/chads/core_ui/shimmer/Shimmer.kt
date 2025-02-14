package ru.chads.core_ui.shimmer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Rect
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun rememberShimmer(
    shimmerBounds: ShimmerBounds,
    theme: ShimmerTheme = LocalShimmerTheme.current,
): Shimmer {
    val effect = rememberShimmerEffect(theme)
    val bounds = rememberShimmerBounds(shimmerBounds)
    val shimmer = remember(theme, effect) {
        Shimmer(theme, effect, bounds)
    }
    shimmer.updateBounds(bounds)
    return shimmer
}

data class Shimmer internal constructor(
    internal val theme: ShimmerTheme,
    internal val effect: ShimmerEffect,
    private val bounds: Rect?,
) {
    internal val boundsFlow = MutableStateFlow(bounds)

    fun updateBounds(bounds: Rect?) {
        boundsFlow.value = bounds
    }
}
