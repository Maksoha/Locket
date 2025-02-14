package ru.chads.core_ui.shimmer

import android.content.res.Resources
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Rect

@Composable
internal fun rememberShimmerBounds(
    shimmerBounds: ShimmerBounds,
): Rect? {
    return when (shimmerBounds) {
        ShimmerBounds.Custom -> Rect.Zero
        ShimmerBounds.View -> null
        ShimmerBounds.Window -> rememberWindowBounds()
    }
}

sealed interface ShimmerBounds {
    data object Custom : ShimmerBounds
    data object View : ShimmerBounds
    data object Window : ShimmerBounds
}

@Composable
internal fun rememberWindowBounds(): Rect = remember {
    val metrics = Resources.getSystem().displayMetrics

    Rect(0f, 0f, metrics.widthPixels.toFloat(), metrics.heightPixels.toFloat())
}

