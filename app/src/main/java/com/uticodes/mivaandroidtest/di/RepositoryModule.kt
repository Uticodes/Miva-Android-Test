package com.uticodes.mivaandroidtest.di

import com.uticodes.mivaandroidtest.data.remote.repository.chapter.ChapterRepository
import com.uticodes.mivaandroidtest.data.remote.repository.chapter.ChapterRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindsChapterRepository(
        chapterRepository: ChapterRepositoryImpl
    ): ChapterRepository
}
