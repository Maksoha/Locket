package ru.chads.locket.di.components

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import ru.chads.background_worker.di.WorkerModule
import ru.chads.background_worker.workers.factory.MyWorkerFactory
import ru.chads.data.di.DataModule
import ru.chads.domain.di.DomainModule
import ru.chads.locket.LocketApplication
import ru.chads.locket.di.modules.AppModule
import javax.inject.Singleton

@Component(
    modules = [AppModule::class, WorkerModule::class, DataModule::class, DomainModule::class]
)
@Singleton
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }
    fun inject(application: LocketApplication)
    fun workerFactory(): MyWorkerFactory
}