package ru.chads.locket.di.components

import dagger.Component
import ru.chads.data.di.DataModule
import ru.chads.feature_feed.di.FeedModule
import ru.chads.locket.ui.MainActivity
import ru.chads.locket.di.FeatureScope
import ru.chads.locket.di.modules.ViewModelModule

@FeatureScope
@Component(modules = [FeedModule::class, DataModule::class, ViewModelModule::class])
interface LocketFeedComponent {
    fun inject(activity: MainActivity)
}