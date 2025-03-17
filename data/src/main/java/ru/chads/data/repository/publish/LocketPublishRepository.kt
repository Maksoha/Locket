package ru.chads.data.repository.publish

import kotlinx.coroutines.flow.Flow
import java.io.File

interface LocketPublishRepository {
    suspend fun publishLocket(image: File): Flow<String>
}