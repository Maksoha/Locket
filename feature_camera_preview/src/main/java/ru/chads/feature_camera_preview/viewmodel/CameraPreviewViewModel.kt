package ru.chads.feature_camera_preview.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


sealed interface State {
    data object PermissionRequired : State
    data class PermissionGranted(
        val needPermission: Boolean = true,
        val isFlashOn: Boolean = true,
        val cameraSelector: CameraSelector = CameraSelector.Front,
    ) : State
}

enum class CameraSelector {
    Front,
    Back;

    operator fun not() = when (this) {
        Front -> Back
        Back -> Front
    }
}

class CameraPreviewViewModel : ViewModel() {

    private val _cameraPreviewState: MutableStateFlow<State> =
        MutableStateFlow(State.PermissionRequired)
    val cameraPreviewState get() = _cameraPreviewState.asStateFlow()

    private val _imageCaptureErrorMessage: MutableSharedFlow<String> = MutableSharedFlow()
    val imageCaptureErrorMessage get() = _imageCaptureErrorMessage.asSharedFlow()

    fun handlePermissionRequestResult(isGranted: Boolean) {
        _cameraPreviewState.update { state ->
            when {
                isGranted && state is State.PermissionRequired -> State.PermissionGranted()
                else -> state
            }
        }
    }

    fun handleImageCaptureFailed(throwable: Throwable) {
        viewModelScope.launch {
            _imageCaptureErrorMessage.emit(throwable.message ?: "Что-то пошло не так")
        }
    }

    fun onFlashChanged(isFlashOn: Boolean) {
        _cameraPreviewState.update { state ->
            requireNotNull(state as? State.PermissionGranted).copy(isFlashOn = isFlashOn)
        }
    }

    fun onCameraFlipped(cameraSelector: CameraSelector) {
        _cameraPreviewState.update { state ->
            requireNotNull(state as? State.PermissionGranted).copy(cameraSelector = cameraSelector)
        }
    }
}