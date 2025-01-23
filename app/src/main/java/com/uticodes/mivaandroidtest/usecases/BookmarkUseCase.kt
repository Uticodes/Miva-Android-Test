package com.uticodes.mivaandroidtest.usecases

import com.uticodes.mivaandroidtest.data.models.Bookmark
import com.uticodes.mivaandroidtest.data.models.Lesson
import com.uticodes.mivaandroidtest.data.remote.repository.bookmark.BookmarkManager
import javax.inject.Inject

class BookmarkUseCase @Inject constructor(
    private val bookmarkManager: BookmarkManager
) {
    fun getBookmarks(lesson: Lesson): List<Bookmark> {
        return bookmarkManager.getBookmarks(lesson)
    }

    fun deleteBookmark(lesson: Lesson, bookmark: Bookmark) =
        bookmarkManager.removeBookmark(lesson, bookmark)

    fun addBookmark(lesson: Lesson, bookmark: Bookmark) =
        bookmarkManager.addBookmark(lesson, bookmark)
}
