package ru.chads.locket.di.modules

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AppModule {

    @Provides
    @Singleton
    fun provideApplicationContext(application: Application): Context =
        application.applicationContext

}