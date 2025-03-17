package ru.chads.background_worker.workers

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.chads.domain.image_uploader.ImageUploader

class WorkManagerImageUploader @Inject constructor(
    private val applicationContext: Context
) : ImageUploader {

    override suspend fun scheduleImageUpload(uri: Uri): Flow<String> {
        val request = OneTimeWorkRequestBuilder<UploadImageWorker>()
            .setInputData(workDataOf(UploadImageWorker.KEY_IMAGE_URI to uri.toString()))
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .build()

        val workManager = WorkManager.getInstance(applicationContext)
        Log.e("check123", "workManager")
        workManager.enqueue(request)

        return flow {
            workManager.getWorkInfoByIdFlow(request.id).collect { workInfo ->
                when (workInfo?.state) {
                    WorkInfo.State.SUCCEEDED -> {
                        val image =
                            workInfo.outputData.getString(UploadImageWorker.KEY_RESULT_FORM_DATA)
                        if (image != null) {
                            emit(image)
                        }
                    }

                    WorkInfo.State.FAILED -> {
                        Log.e("check123", workInfo.outputData.toString())
                    }

                    else -> Unit
                }

            }
        }
    }
}