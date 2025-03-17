package ru.chads.background_worker.workers.factory

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import javax.inject.Inject

class MyWorkerFactory @Inject constructor(
    private val workerFactories: Map<Class<out ListenableWorker>, @JvmSuppressWildcards ChildWorkerFactory>
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker {
        val workerClass = Class.forName(workerClassName).asSubclass(ListenableWorker::class.java)

        val factory = workerFactories.entries
            .firstOrNull { (key, _) -> key.isAssignableFrom(workerClass) }
            ?.value
            ?: throw IllegalArgumentException("Unknown worker class: $workerClassName")

        return factory.create(appContext, workerParameters)
    }
}