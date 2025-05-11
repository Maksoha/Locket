package ru.chads.data.repository.auth_reg

import com.example.network.apis.AuthRegApi
import javax.inject.Inject

class AuthRegRepositoryImpl @Inject constructor(
    private val authRegApi: AuthRegApi
): AuthRegRepository {

    override fun auth(login: String, password: String) {
        TODO("Not yet implemented")
    }

    override fun reg(login: String, password: String) {
        TODO("Not yet implemented")
    }
}