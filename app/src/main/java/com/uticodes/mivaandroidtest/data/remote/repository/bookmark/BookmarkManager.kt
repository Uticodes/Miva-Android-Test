package com.uticodes.mivaandroidtest.data.remote.repository.bookmark

import com.uticodes.mivaandroidtest.data.models.Bookmark
import com.uticodes.mivaandroidtest.data.models.Lesson

interface BookmarkManager {
    fun getBookmarks(lesson: Lesson): List<Bookmark>
    fun addBookmark(lesson: Lesson, bookmark: Bookmark)
    fun removeBookmark(lesson: Lesson, bookmark: Bookmark)
}
