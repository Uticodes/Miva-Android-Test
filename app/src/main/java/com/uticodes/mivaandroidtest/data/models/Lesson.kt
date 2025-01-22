package com.uticodes.mivaandroidtest.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Lesson(
    val title: String,
    val videoUrl: String
)
