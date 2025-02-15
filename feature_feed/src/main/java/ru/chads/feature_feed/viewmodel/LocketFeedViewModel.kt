package ru.chads.feature_feed.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.chads.data.model.LocketInfo
import ru.chads.data.model.TimeoutException
import ru.chads.data.repository.LocketFeedRepository
import ru.chads.data.runSuspendCatching
import ru.chads.navigation.NavCommand
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

sealed interface State {
    data object Loading : State
    data class Error(val message: String) : State
    data class Loaded(val locketSnippets: ImmutableList<LocketInfo>) : State
}

class LocketFeedViewModel @Inject constructor(private val repository: LocketFeedRepository) :
    ViewModel() {

    private val _lockedFeedState: MutableStateFlow<State> = MutableStateFlow(State.Loading)
    val lockedFeedState get() = _lockedFeedState

    private val _snackbarMessage = MutableSharedFlow<String>()
    val snackbarMessage get() = _snackbarMessage.asSharedFlow()

    private val _navCommand = MutableSharedFlow<NavCommand>()
    val navCommand get() = _navCommand.asSharedFlow()


    private var locketItemsLoading = AtomicBoolean(false)

    init {
        fetchLockets()
    }

    fun onAddLocketClick() {
        viewModelScope.launch {
            _navCommand.emit(NavCommand.ToLocketCreator)
        }
    }

    fun onRetryClick() {
        fetchLockets()
    }

    fun checkAndFetchMoreLockets(currentSnippet: Int, totalSnippets: Int) {
        val shouldLoadMore = currentSnippet % 10 == LOAD_MORE_THRESHOLD
        val isNearEndOfList = currentSnippet + 10 > totalSnippets
        if (shouldLoadMore && isNearEndOfList) {
            fetchLockets()
        }
    }

    private fun fetchLockets() {
        if (locketItemsLoading.get()) return
        viewModelScope.launch {
            locketItemsLoading.set(true)
            runSuspendCatching {
                repository.getLockets().flowOn(Dispatchers.IO)
            }.fold(
                onSuccess = { lockets ->
                    lockets.collect { newLockets ->
                        _lockedFeedState.update { state ->
                            when (state) {
                                is State.Loading, is State.Error -> State.Loaded(locketSnippets = newLockets.toImmutableList())
                                is State.Loaded -> state.copy(locketSnippets = (state.locketSnippets + newLockets).toImmutableList())
                            }
                        }
                    }
                },
                onFailure = ::handleError
            )
            locketItemsLoading.set(false)
        }
    }

    private fun handleError(throwable: Throwable) {
        when (throwable) {
            is TimeoutException -> viewModelScope.launch {
                _snackbarMessage.emit(throwable.message ?: DEFAULT_ERROR_MESSAGE)
            }

            else -> _lockedFeedState.update {
                State.Error(throwable.message ?: DEFAULT_ERROR_MESSAGE)
            }
        }
    }

    companion object {
        private const val DEFAULT_ERROR_MESSAGE = "Что-то пошло не так"
        private const val LOAD_MORE_THRESHOLD = 8
    }
}