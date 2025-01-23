package com.uticodes.mivaandroidtest.di

import android.content.res.Resources
import com.uticodes.mivaandroidtest.data.remote.repository.chapter.ChapterRepository
import com.uticodes.mivaandroidtest.usecases.GetChapterUseCase
import com.uticodes.mivaandroidtest.usecases.GetSubjectsUseCase
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

    @Singleton
    @Provides
    fun provideGetSubjectsUseCase(
        resources: Resources
    ): GetSubjectsUseCase = GetSubjectsUseCase(resources)
}
