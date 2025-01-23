package com.uticodes.mivaandroidtest.data.remote.repository.bookmark

import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.set
import com.uticodes.mivaandroidtest.data.models.Bookmark
import com.uticodes.mivaandroidtest.data.models.Lesson
import com.uticodes.mivaandroidtest.utils.Constants.BOOKMARKS
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class BookmarkManagerImpl @Inject constructor(
    private val settings: Settings
) : BookmarkManager {

    private val bookmarks = mutableMapOf<String, MutableList<Bookmark>>()

    init {
        val jsonString: String = settings[BOOKMARKS] ?: ""
        if (jsonString.isNotEmpty()) {
            val tmp: Map<String, MutableList<Bookmark>> = Json.decodeFromString(jsonString)
            bookmarks.putAll(tmp)
        }
    }

    private fun saveBookmarks() {
        val response = Json.encodeToString(bookmarks)
        settings[BOOKMARKS] = response
    }

    override fun getBookmarks(lesson: Lesson): List<Bookmark> {
        return bookmarks[lesson.videoUrl] ?: listOf()
    }

    override fun addBookmark(lesson: Lesson, bookmark: Bookmark) {
        if (!bookmarks.containsKey(lesson.videoUrl)) {
            bookmarks[lesson.videoUrl] = mutableListOf()
        }
        bookmarks[lesson.videoUrl]?.add(bookmark)

        saveBookmarks()
    }

    override fun removeBookmark(lesson: Lesson, bookmark: Bookmark) {
        bookmarks[lesson.videoUrl]?.remove(bookmark)
        saveBookmarks()
    }
}
