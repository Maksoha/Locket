package ru.chads.background_worker.di

import androidx.work.ListenableWorker
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import ru.chads.background_worker.workers.UploadImageWorker
import ru.chads.background_worker.workers.WorkManagerImageUploader
import ru.chads.background_worker.workers.factory.ChildWorkerFactory
import ru.chads.background_worker.workers.factory.MyWorkerFactory
import ru.chads.domain.image_uploader.ImageUploader
import kotlin.reflect.KClass

@Module
interface WorkerModule {



    @Binds
    @IntoMap
    @WorkerKey(UploadImageWorker::class)
    fun bindUploadImageWorker(factory: UploadImageWorker.Factory): ChildWorkerFactory

    @Binds
    fun bindImageUploader(
        impl: WorkManagerImageUploader
    ): ImageUploader

    companion object {
        @Provides
        fun provideWorkerFactory(
            workerFactories: Map<Class<out ListenableWorker>, @JvmSuppressWildcards ChildWorkerFactory>
        ): MyWorkerFactory {
            return MyWorkerFactory(workerFactories)
        }
    }

}

@MapKey
@Retention(AnnotationRetention.RUNTIME)
annotation class WorkerKey(val value: KClass<out ListenableWorker>)