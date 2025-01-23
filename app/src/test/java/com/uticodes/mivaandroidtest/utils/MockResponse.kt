package com.uticodes.mivaandroidtest.utils

import com.uticodes.mivaandroidtest.data.models.Chapter
import com.uticodes.mivaandroidtest.data.models.Lesson

fun generateMockChapters(numberOfChapters: Int, lessonsPerChapter: Int): List<Chapter> {
    return (1..numberOfChapters).map { chapterIndex ->
        Chapter(
            title = "Chapter $chapterIndex",
            lessons = (1..lessonsPerChapter).map { lessonIndex ->
                Lesson(
                    title = "Lesson $lessonIndex",
                    videoUrl = "example_url_$lessonIndex"
                )
            }
        )
    }
}
