package ru.chads.feature_camera_preview.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.chads.navigation.NavCommand


sealed interface State {
    data object PermissionRequired : State
    data class PermissionGranted(
        val imageUri: Uri? = null,
        val isFlashOn: Boolean = true,
        val cameraSelector: CameraSelector = CameraSelector.Back,
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

    private val _state: MutableStateFlow<State> =
        MutableStateFlow(State.PermissionRequired)
    val state get() = _state.asStateFlow()

    private val _imageCaptureErrorMessage: MutableSharedFlow<String> = MutableSharedFlow()
    val imageCaptureErrorMessage get() = _imageCaptureErrorMessage.asSharedFlow()

    private val _navCommand = MutableSharedFlow<NavCommand>()
    val navCommand get() = _navCommand.asSharedFlow()

    fun handlePermissionRequestResult(isGranted: Boolean) {
        _state.update { state ->
            when {
                isGranted && state is State.PermissionRequired -> State.PermissionGranted()
                else -> state
            }
        }
    }

    fun handleImageSavedFailed(throwable: Throwable) {
        viewModelScope.launch {
            _imageCaptureErrorMessage.emit(throwable.message ?: "Что-то пошло не так")
        }
    }

    fun onImageSaved(imageUri: Uri) {
        _state.update { state ->
            state.asPermissionGranted().copy(imageUri = imageUri)
        }
        viewModelScope.launch {
            _navCommand.emit(NavCommand.RouterCommand.ToLocketEditor(imageUri))
        }
    }

    fun onFlashChanged(isFlashOn: Boolean) {
        _state.update { state ->
            state.asPermissionGranted().copy(isFlashOn = isFlashOn)
        }
    }

    fun onCameraFlipped(cameraSelector: CameraSelector) {
        _state.update { state ->
            state.asPermissionGranted().copy(cameraSelector = cameraSelector)
        }
    }

    private fun State.asPermissionGranted() = requireNotNull(this as? State.PermissionGranted)
}