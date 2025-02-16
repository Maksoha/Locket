package ru.chads.data.repository.feed

import kotlinx.coroutines.flow.Flow
import ru.chads.data.model.LocketInfo

interface LocketFeedRepository {
    fun getLockets(): Flow<List<LocketInfo>>
}