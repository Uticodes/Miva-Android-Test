package com.uticodes.mivaandroidtest.usecases

import com.uticodes.mivaandroidtest.data.remote.repository.chapter.ChapterRepository
import com.uticodes.mivaandroidtest.utils.generateMockChapters
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetChapterUseCaseTest {

    private lateinit var chapterUseCase: GetChapterUseCase
    private lateinit var chapterRepository: ChapterRepository

    @Before
    fun setup() {
        chapterRepository = mockk()
        chapterUseCase = GetChapterUseCase(chapterRepository)
    }

    @Test
    fun `getChapters should return list of chapters on success`(): Unit = runBlocking {

        val mockChapters = generateMockChapters(numberOfChapters = 3, lessonsPerChapter = 3)

        coEvery { chapterRepository.getChapters() } returns flowOf(Result.success(mockChapters))

        val result = chapterUseCase.getChapters()

        result.collect { flowResult ->
            assertTrue(flowResult.isSuccess)
            assertEquals(mockChapters, flowResult.getOrNull())
        }
    }

    @Test
    fun `getChapters should return error on failure`(): Unit = runBlocking {

        val exception = Exception("Network Error")
        coEvery { chapterRepository.getChapters() } returns flowOf(Result.failure(exception))

        val result = chapterUseCase.getChapters()

        result.collect { flowResult ->
            assertTrue(flowResult.isFailure)
            assertEquals(exception, flowResult.exceptionOrNull())
        }
    }

    @After
    fun tearDown() {
        unmockkAll()
    }
}
