package ru.chads.data.repository

import kotlinx.coroutines.flow.Flow
import ru.chads.data.model.LocketInfo

interface LocketFeedRepository {
    fun getLockets(): Flow<List<LocketInfo>>
}