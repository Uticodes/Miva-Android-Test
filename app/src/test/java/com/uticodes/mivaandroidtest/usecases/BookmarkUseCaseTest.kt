package com.uticodes.mivaandroidtest.usecases

import com.uticodes.mivaandroidtest.data.models.Bookmark
import com.uticodes.mivaandroidtest.data.models.Lesson
import com.uticodes.mivaandroidtest.data.remote.repository.bookmark.BookmarkManager
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class BookmarkUseCaseTest {

    private lateinit var bookmarkManager: BookmarkManager

    private lateinit var bookmarkUseCase: BookmarkUseCase

    private val lesson = Lesson("Lesson 1", "https://example.com/video1")
    private val bookmark = Bookmark("Test Note", timestamp = 1000)

    @Before
    fun setUp() {
        bookmarkManager = mockk()
        bookmarkUseCase = BookmarkUseCase(bookmarkManager)
    }

    @Test
    fun `getBookmarks should return bookmarks from BookmarkManager`() {

        every { bookmarkManager.getBookmarks(lesson) } returns listOf(bookmark)

        val bookmarks = bookmarkUseCase.getBookmarks(lesson)

        assertEquals(1, bookmarks.size)
        assertEquals(bookmark, bookmarks.first())
        verify { bookmarkManager.getBookmarks(lesson) }
    }

    @Test
    fun `getBookmarks should return bookmarks and call BookmarkManager`() {

        every { bookmarkManager.getBookmarks(lesson) } returns listOf(bookmark)

        val result = bookmarkUseCase.getBookmarks(lesson)

        assertEquals(listOf(bookmark), result)
        verify { bookmarkManager.getBookmarks(lesson) }
    }

    @Test
    fun `addBookmark calls BookmarkManager with the correct lesson and bookmark`() {
        every { bookmarkManager.addBookmark(lesson, bookmark) } just Runs

        bookmarkUseCase.addBookmark(lesson, bookmark)

        verify { bookmarkManager.addBookmark(lesson, bookmark) }
    }

    @Test
    fun `deleteBookmark calls BookmarkManager with the correct lesson and bookmark`() {

        every { bookmarkManager.removeBookmark(lesson, bookmark) } just Runs

        bookmarkUseCase.deleteBookmark(lesson, bookmark)

        verify { bookmarkManager.removeBookmark(lesson, bookmark) }
    }

    @After
    fun tearDown() {
        unmockkAll()
    }
}
