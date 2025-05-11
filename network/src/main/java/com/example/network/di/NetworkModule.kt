package com.example.network.di

import com.example.network.apis.AuthRegApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

//@Module
//object NetworkModule {
//
//    @Provides
//    @Singleton
//    fun provideRetrofit(): Retrofit =
//        Retrofit.Builder()
//            .baseUrl("https://your.api/")
//            .build()
//
//    @Provides
//    @Singleton
//    fun provideAuthRegApi(retrofit: Retrofit): AuthRegApi =
//        retrofit.create(AuthRegApi::class.java)
//}
