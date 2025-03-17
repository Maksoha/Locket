package ru.chads.domain.di

import dagger.Binds
import dagger.Module
import ru.chads.domain.use_cases.UploadImageUseCase
import ru.chads.domain.use_cases.UploadImageUseCaseImpl

@Module
interface DomainModule {

    @Binds
    fun bindUploadImageUseCase(
        impl: UploadImageUseCaseImpl
    ): UploadImageUseCase
}