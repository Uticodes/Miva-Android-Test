package com.uticodes.mivaandroidtest.usecases

import com.uticodes.mivaandroidtest.data.models.Chapter
import com.uticodes.mivaandroidtest.data.remote.repository.chapter.ChapterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetChapterUseCase@Inject constructor(
    private val chapterRepository: ChapterRepository
) {

    suspend fun getChapters(): Flow<Result<List<Chapter>>> {
        return chapterRepository.getChapters()
    }
}
