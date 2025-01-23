package com.uticodes.mivaandroidtest.di

import com.uticodes.mivaandroidtest.data.remote.repository.bookmark.BookmarkManager
import com.uticodes.mivaandroidtest.data.remote.repository.bookmark.BookmarkManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun bindsBookmarkManager(
        bookmarkManager: BookmarkManagerImpl
    ): BookmarkManager
}
