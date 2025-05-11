package com.example.auth_reg.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import com.example.auth_reg.AuthRegViewModel

@Module
abstract class AuthRegModule {

    @Binds
    abstract fun bindViewModel(vm: AuthRegViewModel): ViewModel

    companion object {
        @Provides
        fun provideViewModelKey(): String = ViewModelKey

        private const val ViewModelKey = "AuthRegViewModel"
    }
}
