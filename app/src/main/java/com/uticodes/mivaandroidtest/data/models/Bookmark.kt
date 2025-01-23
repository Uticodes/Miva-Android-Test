package com.uticodes.mivaandroidtest.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Bookmark(
    val note: String,
    val timestamp: Long,
)
