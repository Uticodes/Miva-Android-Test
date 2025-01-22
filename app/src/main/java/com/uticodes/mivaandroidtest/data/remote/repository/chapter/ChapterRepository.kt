package com.uticodes.mivaandroidtest.data.remote.repository.chapter

import com.uticodes.mivaandroidtest.data.models.Chapter
import kotlinx.coroutines.flow.Flow

interface ChapterRepository {
    suspend fun getChapters(): Flow<Result<List<Chapter>>>
}
