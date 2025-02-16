package ru.chads.feature_camera_preview.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import ru.chads.core_ui.theme.DayNightPreview
import ru.chads.core_ui.theme.LocketTheme

@Composable
fun CaptureButton(
    modifier: Modifier = Modifier,
    onCapture: () -> Unit,
) {
    var isPressed by remember { mutableStateOf(false) }
    val innerCircleSize by animateDpAsState(if (isPressed) 60.dp else 64.dp)

    Box(
        modifier = modifier
            .size(80.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surface)
            .border(width = 5.dp, color = MaterialTheme.colorScheme.onSurface, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(innerCircleSize)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.onSurface)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = {
                            isPressed = true
                            val pressSuccessful = tryAwaitRelease()
                            isPressed = false
                            if (pressSuccessful) {
                                onCapture()
                            }
                        }
                    )
                }
        )
    }
}

@DayNightPreview
@Composable
private fun Preview() {
    LocketTheme {
        CaptureButton { }
    }
}