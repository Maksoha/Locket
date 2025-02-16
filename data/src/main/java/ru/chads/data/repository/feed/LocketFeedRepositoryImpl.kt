package ru.chads.data.repository.feed

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import ru.chads.data.model.LocketInfo
import javax.inject.Inject

class LocketFeedRepositoryImpl @Inject constructor() : LocketFeedRepository {
    override fun getLockets(): Flow<List<LocketInfo>> =
        flowOf(
            mutableListOf<LocketInfo>().apply {
                repeat(10) {
                    add(
                        LocketInfo(
                            imageUrl = "https://images.unsplash.com/photo-1543807535-eceef0bc6599?q=80&w=3387&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                            userAvatarUrl = "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?q=80&w=3560&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                            username = "username$it",
                            description = "description"
                        )
                    )
                }
            }
        )
}