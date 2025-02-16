package ru.chads.data.di

import dagger.Binds
import dagger.Module
import ru.chads.data.repository.feed.LocketFeedRepositoryImpl
import ru.chads.data.repository.feed.LocketFeedRepository

@Module
abstract class DataModule {

    @Binds
    abstract fun bindLocketFeedRepository(
        impl: LocketFeedRepositoryImpl
    ): LocketFeedRepository

}