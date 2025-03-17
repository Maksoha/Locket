package ru.chads.background_worker.workers

import android.content.Context
import android.net.Uri
import androidx.core.net.toFile
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.default
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import ru.chads.background_worker.workers.factory.ChildWorkerFactory
import ru.chads.coroutines.runSuspendCatching
import ru.chads.domain.use_cases.UploadImageUseCase
import java.io.File
import javax.inject.Inject

class UploadImageWorker @Inject constructor(
    context: Context,
    params: WorkerParameters,
    private val uploadImageUseCase: UploadImageUseCase
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {

        val imageUri =
            inputData.getString(KEY_IMAGE_URI)?.let(Uri::parse) ?: return Result.failure()

        val imageFile: File = withContext(Dispatchers.IO) {
            Compressor.compress(context = applicationContext, imageFile = imageUri.toFile()) {
                default()
            }
        }

        return try {
            val image = uploadImageUseCase.uploadImage(imageFile).first()
            Result.success(workDataOf(KEY_RESULT_FORM_DATA to image))
        } catch (e: Exception) {
            Result.failure()
        }
    }

    companion object {
        const val KEY_IMAGE_URI = "KEY_IMAGE_URI"
        const val KEY_RESULT_FORM_DATA = "result_form_data"
    }

    class Factory @Inject constructor(
        private val uploadImageUseCase: UploadImageUseCase
    ) : ChildWorkerFactory {
        override fun create(context: Context, params: WorkerParameters): ListenableWorker =
            UploadImageWorker(context, params, uploadImageUseCase)
    }
}