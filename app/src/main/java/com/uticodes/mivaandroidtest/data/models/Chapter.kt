package com.uticodes.mivaandroidtest.data.models
import kotlinx.serialization.Serializable

@Serializable
data class Chapter(
    val title: String,
    val lessons: List<Lesson>
)
