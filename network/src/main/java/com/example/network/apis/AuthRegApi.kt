package com.example.network.apis

import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface AuthRegApi {

    @GET
    suspend fun auth(login: String, password: String): Flow<AuthRegAnswer>

    @GET
    suspend fun reg(login: String, password: String): Flow<AuthRegAnswer>

}

data class AuthRegAnswer (
    val token: String
)