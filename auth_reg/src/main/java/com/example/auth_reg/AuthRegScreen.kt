package com.example.auth_reg

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.auth_reg.ui.theme.LocketTheme
import kotlinx.coroutines.flow.collectLatest
import ru.chads.navigation.NavCommand

@Composable
fun AuthRegScreen(
    viewModel: AuthRegViewModel,
    navController: NavController
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.navCommand.collectLatest { navCommand ->
            when (navCommand) {
                is NavCommand.RouterCommand -> navController.navigate(navCommand.route)
                NavCommand.GoBack -> navController.popBackStack()
            }
        }
    }

    AuthRegScreenContent(
        title = state.title,
        login = state.login,
        onLoginChanged = viewModel::onLoginChanged,
        password = state.password,
        onPasswordChanged = viewModel::onPasswordChanged,
        showConfirmPassword = state.showConfirmPassword,
        confirmPassword = state.confirmPassword,
        onConfirmPasswordChanged = viewModel::onConfirmPasswordChanged,
        authRegButtonClicked = viewModel::authRegButtonClicked,
        authRegButtonText = state.authRegButtonText,
        authToRegOfferText = state.authToRegOfferText,
        authToRegOfferClicked = viewModel::authToRegOfferClicked
    )
}

@Composable
fun AuthRegScreenContent(
    title: String,
    login: String,
    onLoginChanged: (String) -> Unit,
    password: String,
    onPasswordChanged: (String) -> Unit,
    showConfirmPassword: Boolean,
    confirmPassword: String,
    onConfirmPasswordChanged: (String) -> Unit,
    authRegButtonClicked: () -> Unit,
    authRegButtonText: String,
    authToRegOfferText: String,
    authToRegOfferClicked: () -> Unit
) {
    Box(
        Modifier
            .fillMaxSize() // Занимает весь экран
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center) // Центрируем по вертикали и горизонтали
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                title,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(Modifier.height(24.dp))
            OutlinedTextField(
                value = login,
                onValueChange = onLoginChanged,
                label = { Text("Логин") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(
                value = password,
                onValueChange = onPasswordChanged,
                label = { Text("Пароль") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()
            )
            if (showConfirmPassword) {
                Spacer(Modifier.height(12.dp))
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = onConfirmPasswordChanged,
                    label = { Text("Повторите пароль") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation()
                )
            }
            Spacer(Modifier.height(24.dp))
            Button(
                onClick = authRegButtonClicked,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(authRegButtonText)
            }
            Spacer(Modifier.height(24.dp))
        }
        Text(
            authToRegOfferText,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
                .clickable { authToRegOfferClicked() }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AuthScreenPreview() {
    LocketTheme {
        AuthRegScreenContent(
            title = "Вход",
            login = "Login",
            onLoginChanged = {},
            password = "password",
            onPasswordChanged = {},
            showConfirmPassword = false,
            confirmPassword = "",
            onConfirmPasswordChanged = {},
            authRegButtonClicked = {},
            authRegButtonText = "Авторизоваться",
            authToRegOfferText = regOffer,
            authToRegOfferClicked = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RegScreenPreview() {
    LocketTheme {
        AuthRegScreenContent(
            title = "Вход",
            login = "Login",
            onLoginChanged = {},
            password = "password",
            onPasswordChanged = {},
            showConfirmPassword = true,
            confirmPassword = "password",
            onConfirmPasswordChanged = {},
            authRegButtonClicked = {},
            authRegButtonText = "Зарегистрироваться",
            authToRegOfferText = authOffer,
            authToRegOfferClicked = {}
        )
    }
}