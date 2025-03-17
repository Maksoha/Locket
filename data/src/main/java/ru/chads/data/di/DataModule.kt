package ru.chads.data.di

import dagger.Binds
import dagger.Module
import ru.chads.data.repository.feed.LocketFeedRepository
import ru.chads.data.repository.feed.LocketFeedRepositoryImpl
import ru.chads.data.repository.publish.LocketPublishRepository
import ru.chads.data.repository.publish.LocketPublishRepositoryImpl

@Module
abstract class DataModule {

    @Binds
    abstract fun bindLocketFeedRepository(
        impl: LocketFeedRepositoryImpl
    ): LocketFeedRepository

    @Binds
    abstract fun bindLocketPublishRepository(
        impl: LocketPublishRepositoryImpl
    ): LocketPublishRepository

}