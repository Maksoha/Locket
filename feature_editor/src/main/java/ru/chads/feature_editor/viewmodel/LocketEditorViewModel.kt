package ru.chads.feature_editor.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.chads.navigation.NavCommand
import javax.inject.Inject

data class State(
    val imageUri: Uri,
    val description: String = "",
    val hasPhotoSubmitted: Boolean = false
)

class LocketEditorViewModel @Inject constructor(imageUri: Uri) : ViewModel() {
    private val _state: MutableStateFlow<State> = MutableStateFlow(State(imageUri = imageUri))
    val state: StateFlow<State> get() = _state.asStateFlow()

    private val _navCommand: MutableSharedFlow<NavCommand> = MutableSharedFlow()
    val navCommand: SharedFlow<NavCommand> get() = _navCommand.asSharedFlow()

    fun onDescriptionChanged(description: String) {
        _state.update { state ->
            state.copy(description = description)
        }
    }

    fun onPublishClick() {
        _state.update { state ->
            state.copy(hasPhotoSubmitted = true)
        }
    }

    fun onBackClick() {
        viewModelScope.launch {
            _navCommand.emit(NavCommand.GoBack)
        }
    }
}

class LocketEditorViewModelFactory(
    private val imageUri: Uri,
    private val viewModelProviderFactory: ViewModelProvider.Factory
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LocketEditorViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LocketEditorViewModel(imageUri) as T
        }
        return viewModelProviderFactory.create(modelClass)
    }
}