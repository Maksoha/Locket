package com.example.auth_reg

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.chads.coroutines.runSuspendCatching
import ru.chads.data.repository.auth_reg.AuthRegRepository
import ru.chads.navigation.NavCommand
import javax.inject.Inject

data class State(
    val isLogin: Boolean = true,
    val title: String = "Вход",
    val login: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val showConfirmPassword: Boolean = false,
    val authRegButtonText: String = "Авторизоваться",
    val authToRegOfferText: String = regOffer,
    val errorText: String = "",
    val showError: Boolean = false
)

const val regOffer: String = "Нет аккаунта? Зарегистрироваться"
const val authOffer: String = "Нет аккаунта? Зарегистрироваться"

class AuthRegViewModel @Inject constructor(
    private val authRegRepository: AuthRegRepository,
): ViewModel() {

    private val _state: MutableStateFlow<State> = MutableStateFlow(State())
    val state: StateFlow<State> get() = _state.asStateFlow()

    private val _navCommand: MutableSharedFlow<NavCommand> = MutableSharedFlow()
    val navCommand: SharedFlow<NavCommand> get() = _navCommand.asSharedFlow()

    fun onLoginChanged(login: String) {
        _state.update { state ->
            state.copy(login = login)
        }
    }

    fun onPasswordChanged(password: String) {
        _state.update { state ->
            state.copy(password = password)
        }
    }

    fun onConfirmPasswordChanged(confirmPassword: String) {
        _state.update { state ->
            state.copy(confirmPassword = confirmPassword)
        }
    }

    fun authRegButtonClicked() {

        _state.update { state ->
            state.copy(
                errorText = "",
                showError = false
            )
        }

        viewModelScope.launch {

            runSuspendCatching {
                val state = state.value
                if (state.isLogin)
                    authRegRepository.auth(state.login, state.password)
                else
                    authRegRepository.reg(state.login, state.password)
            }.fold(
                onSuccess = {
                    _navCommand.emit(NavCommand.RouterCommand.ToLocketFeed)
                },
                onFailure = { exception ->
                    _state.update { state ->
                        state.copy(
                            errorText = exception.message.toString(),
                            showError = true
                        )
                    }
                }
            )
        }
    }

    fun authToRegOfferClicked() {
        _state.update { state ->
            if (state.isLogin) {
                state.copy(
                    isLogin = false,
                    title = "Регистрация",
                    showConfirmPassword = true,
                    authRegButtonText = "Зарегистрироваться",
                    authToRegOfferText = authOffer
                )
            } else {
                state.copy(
                    isLogin = true,
                    title = "Авторизация",
                    confirmPassword = "",
                    showConfirmPassword = false,
                    authRegButtonText = "Авторизоваться",
                    authToRegOfferText = regOffer
                )
            }
        }
    }

}