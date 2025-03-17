package ru.chads.domain.image_uploader

import android.net.Uri
import kotlinx.coroutines.flow.Flow

interface ImageUploader {
    suspend fun scheduleImageUpload(uri: Uri): Flow<String>
}