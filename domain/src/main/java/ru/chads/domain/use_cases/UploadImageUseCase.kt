package ru.chads.domain.use_cases

import kotlinx.coroutines.flow.Flow
import java.io.File

interface UploadImageUseCase {
    suspend fun uploadImage(imageFile: File): Flow<String>
}