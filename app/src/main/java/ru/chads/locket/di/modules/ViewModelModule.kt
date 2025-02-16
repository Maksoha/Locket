package ru.chads.locket.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.chads.feature_feed.viewmodel.LocketFeedViewModel
import ru.chads.locket.di.ViewModelKey
import ru.chads.locket.factory.ViewModelFactory

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(LocketFeedViewModel::class)
    abstract fun bindLocketFeedViewModel(viewModel: LocketFeedViewModel): ViewModel

}
