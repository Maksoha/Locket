package ru.chads.domain.use_cases

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import ru.chads.data.repository.publish.LocketPublishRepository
import java.io.File

class UploadImageUseCaseImpl @Inject constructor(
    private val locketPublishRepository: LocketPublishRepository
) : UploadImageUseCase {
    override suspend fun uploadImage(imageFile: File): Flow<String> =
        locketPublishRepository.publishLocket(imageFile)

}