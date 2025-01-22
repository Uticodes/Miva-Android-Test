package com.uticodes.mivaandroidtest.data.remote

import com.uticodes.mivaandroidtest.data.models.Chapter
import retrofit2.http.GET

interface ApiService {
    @GET("chapters")
    suspend fun getChapters(): List<Chapter>
}
