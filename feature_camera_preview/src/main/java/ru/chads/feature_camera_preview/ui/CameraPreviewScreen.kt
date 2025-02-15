package ru.chads.feature_camera_preview.ui

import android.Manifest
import android.graphics.Bitmap
import androidx.camera.core.ImageCapture.OnImageCapturedCallback
import androidx.camera.core.ImageCaptureException
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FlashOff
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.FlipCameraAndroid
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.collectLatest
import ru.chads.core_ui.permissionRequester
import ru.chads.core_ui.theme.DayNightPreview
import ru.chads.core_ui.theme.LocketTheme
import ru.chads.feature_camera_preview.ui.components.CameraPreview
import ru.chads.feature_camera_preview.ui.components.CaptureButton
import ru.chads.feature_camera_preview.viewmodel.CameraPreviewViewModel
import ru.chads.feature_camera_preview.viewmodel.CameraSelector
import ru.chads.feature_camera_preview.viewmodel.State
import ru.chads.navigation.NavCommand
import androidx.camera.core.CameraSelector as androidCameraSelector

@Composable
fun LocketCreatorScreen(
    navController: NavController,
    snackbarHostState: SnackbarHostState,
    viewModel: CameraPreviewViewModel = viewModel(),
) {
    LocketTheme(darkTheme = true) {
        val state by viewModel.cameraPreviewState.collectAsStateWithLifecycle()

        val context = LocalContext.current
        val cameraController = remember {
            LifecycleCameraController(context).apply {
                setEnabledUseCases(CameraController.IMAGE_CAPTURE)
            }
        }

        val requestPermission = permissionRequester(
            permission = Manifest.permission.CAMERA,
            onResult = viewModel::handlePermissionRequestResult
        )

        LaunchedEffect(Unit) {
            requestPermission()
        }

        LaunchedEffect(Unit) {
            viewModel.imageCaptureErrorMessage.collectLatest { errorMsg ->
                snackbarHostState.showSnackbar(errorMsg)
            }
        }

        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            bottomBar = {
                (state as? State.PermissionGranted)?.let { state ->
                    BottomBar(
                        modifier = Modifier.padding(bottom = 16.dp),
                        cameraController = cameraController,
                        isFlashOn = state.isFlashOn,
                        cameraSelector = state.cameraSelector,
                        onFlashChanged = viewModel::onFlashChanged,
                        onPhotoTaken = {
                            cameraController.takePicture(
                                ContextCompat.getMainExecutor(context),
                                object : OnImageCapturedCallback() {
                                    override fun onPostviewBitmapAvailable(bitmap: Bitmap) {
                                        navController.navigate(NavCommand.ToLocketEditor.route)
                                    }

                                    override fun onError(exception: ImageCaptureException) {
                                        viewModel.handleImageCaptureFailed(exception)
                                    }
                                }
                            )
                        },
                        onCameraFlipped = viewModel::onCameraFlipped
                    )
                }
            },
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CameraPreview(
                    controller = cameraController,
                    modifier = Modifier
                        .offset(y = CameraPreviewYOffset)
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(24.dp))
                )
            }
        }
    }
}

@Composable
fun BottomBar(
    cameraController: LifecycleCameraController,
    isFlashOn: Boolean,
    cameraSelector: CameraSelector,
    onFlashChanged: (Boolean) -> Unit,
    onPhotoTaken: () -> Unit,
    onCameraFlipped: (CameraSelector) -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(cameraSelector) {
        cameraController.cameraSelector = cameraSelector.toAndroidCameraSelector()
    }

    LaunchedEffect(isFlashOn) {
        cameraController.enableTorch(isFlashOn)
    }

    Row(
        modifier = modifier
            .navigationBarsPadding()
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { onFlashChanged(isFlashOn.not()) },
            modifier = Modifier.size(IconButtonSize),
        ) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                imageVector = when {
                    isFlashOn -> Icons.Default.FlashOn
                    else -> Icons.Default.FlashOff
                },
                contentDescription = null
            )
        }
        CaptureButton(onCapture = onPhotoTaken)
        IconButton(
            onClick = { onCameraFlipped(cameraSelector.not()) },
            modifier = Modifier.size(IconButtonSize),
        ) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                imageVector = Icons.Default.FlipCameraAndroid,
                contentDescription = null
            )
        }
    }
}

private fun CameraSelector.toAndroidCameraSelector() = when (this) {
    CameraSelector.Front -> androidCameraSelector.DEFAULT_BACK_CAMERA
    CameraSelector.Back -> androidCameraSelector.DEFAULT_FRONT_CAMERA
}

private val IconButtonSize = 36.dp
private val CameraPreviewYOffset = (-48).dp

@DayNightPreview
@Composable
private fun Preview() {
    LocketTheme {
        LocketCreatorScreen(
            snackbarHostState = remember { SnackbarHostState() },
            navController = rememberNavController()
        )
    }
}