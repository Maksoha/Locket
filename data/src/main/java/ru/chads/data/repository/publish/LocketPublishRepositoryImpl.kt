package ru.chads.data.repository.publish

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.io.File
import javax.inject.Inject

class LocketPublishRepositoryImpl @Inject constructor() : LocketPublishRepository {
    override suspend fun publishLocket(image: File): Flow<String> = flowOf("")
}

            /*val requestBody = imageFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val multipartBody = MultipartBody.Part.createFormData(
                "image", imageFile.name, requestBody
            )

            val response = apiService.uploadImage(multipartBody)
            response.isSuccessful*/


