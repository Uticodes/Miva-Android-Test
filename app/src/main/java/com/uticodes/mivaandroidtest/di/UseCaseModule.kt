package com.uticodes.mivaandroidtest.di

import com.uticodes.mivaandroidtest.data.remote.repository.chapter.ChapterRepository
import com.uticodes.mivaandroidtest.usecases.GetChapterUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun provideGetChapterUseCase(
        chapterRepository: ChapterRepository
    ): GetChapterUseCase = GetChapterUseCase(chapterRepository)
}
