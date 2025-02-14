package ru.chads.feature_feed.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.chads.feature_feed.LocketFeedViewModel

@Module
abstract class FeedModule {

    @Binds
    abstract fun bindViewModel(vm: LocketFeedViewModel): ViewModel

    companion object {
        @Provides
        fun provideKey(): String = ViewModelKey

        private const val ViewModelKey = "LocketFeedViewModel"
    }
}