package com.uticodes.mivaandroidtest.usecases

import com.russhwolf.settings.Settings
import com.russhwolf.settings.contains
import com.russhwolf.settings.get
import com.russhwolf.settings.set
import com.uticodes.mivaandroidtest.data.models.LearningProgress
import com.uticodes.mivaandroidtest.data.models.Lesson
import com.uticodes.mivaandroidtest.utils.Constants.LESSON_PROGRESS
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class ResumeLearningUseCase @Inject constructor(
    private val settings: Settings
) {

    fun saveLessonProgress(lesson: Lesson, timestamp: Long) {
        val jsonString = Json.encodeToString(
            LearningProgress(
                lesson = lesson,
                timestamp = timestamp
            )
        )

        settings[LESSON_PROGRESS] = jsonString
    }

    fun getSavedProgress(): LearningProgress? {
        val jsonString: String? = settings[LESSON_PROGRESS]
        return jsonString?.let { Json.decodeFromString(it) }
    }

    fun clearProgress() {
        settings.remove(LESSON_PROGRESS)
    }
}
