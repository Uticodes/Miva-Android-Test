package com.uticodes.mivaandroidtest.data.remote.repository.chapter

import com.uticodes.mivaandroidtest.data.models.Chapter
import com.uticodes.mivaandroidtest.data.remote.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ChapterRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ChapterRepository {

    override suspend fun getChapters(): Flow<Result<List<Chapter>>> = flow {
        try {
            val chapters = apiService.getChapters()
            emit(Result.success(chapters))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}
