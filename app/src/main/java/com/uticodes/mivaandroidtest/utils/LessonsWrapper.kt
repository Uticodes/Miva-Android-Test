package com.uticodes.mivaandroidtest.utils

import com.uticodes.mivaandroidtest.data.models.Lesson
import kotlinx.serialization.Serializable

@Serializable
data class LessonsWrapper(
    val lessons: List<Lesson>
) {
    operator fun get(index: Int): Lesson {
        return lessons[index]
    }
}
