package ru.chads.locket

import android.app.Application
import android.util.Log
import androidx.work.Configuration
import androidx.work.WorkManager
import javax.inject.Inject
import ru.chads.background_worker.workers.factory.MyWorkerFactory
import ru.chads.domain.image_uploader.ImageUploader
import ru.chads.locket.di.components.DaggerAppComponent

class LocketApplication : Application(), Configuration.Provider {

    private val myWorkerFactory: MyWorkerFactory by lazy {
        DaggerAppComponent.factory().create(this).workerFactory()
    }

    @Inject
    lateinit var imageUploader: ImageUploader

    override val workManagerConfiguration: Configuration
        get() {
            Log.e("check123", "123")
            return Configuration.Builder()
                .setWorkerFactory(myWorkerFactory)
                .build()
        }

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.factory()
            .create(this)
            .inject(this)

//        WorkManager.initialize(this, workManagerConfiguration)
    }
}