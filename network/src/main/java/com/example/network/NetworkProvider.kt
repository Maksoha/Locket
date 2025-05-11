package com.example.network

import com.example.network.apis.AuthRegApi
import retrofit2.Retrofit

object NetworkProvider {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://your.api/")
        .build()

    val authRegApi: AuthRegApi =
        retrofit.create(AuthRegApi::class.java)

}