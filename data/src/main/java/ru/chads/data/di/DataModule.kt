package ru.chads.data.di

import dagger.Binds
import dagger.Module
import ru.chads.data.repository.LocketFeedRepositoryImpl
import ru.chads.data.repository.LocketFeedRepository

@Module
abstract class DataModule {

    @Binds
    abstract fun bindLocketFeedRepository(
        impl: LocketFeedRepositoryImpl
    ): LocketFeedRepository

}