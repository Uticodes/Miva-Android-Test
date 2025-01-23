package com.uticodes.mivaandroidtest.utils

import android.content.Context
import androidx.media3.database.DatabaseProvider
import androidx.media3.database.StandaloneDatabaseProvider
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DataSource.Factory
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.cache.Cache
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.datasource.cache.NoOpCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import androidx.media3.exoplayer.offline.DownloadManager
import androidx.media3.exoplayer.offline.DownloadNotificationHelper
import com.uticodes.mivaandroidtest.data.local.VideoDownloadService
import java.io.File
import java.net.CookieHandler
import java.net.CookieManager
import java.net.CookiePolicy
import java.util.concurrent.Executors
import javax.inject.Singleton

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
@Singleton
class PlayerUtil {
    private lateinit var databaseProvider: DatabaseProvider
    private lateinit var httpDataSourceFactory: Factory
    private lateinit var dataSourceFactory: Factory
    private lateinit var downloadCache: Cache
    private lateinit var downloadManager: DownloadManager
    private lateinit var cookieManager: CookieManager
    private lateinit var downloadNotificationHelper: DownloadNotificationHelper

    private fun getHttpDataSourceFactory(): Factory {
        if (!::httpDataSourceFactory.isInitialized) {
            cookieManager = CookieManager()
            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER)
            CookieHandler.setDefault(cookieManager)
            httpDataSourceFactory = DefaultHttpDataSource.Factory()
        }

        return httpDataSourceFactory
    }

    fun getDataSourceFactory(context: Context): DataSource.Factory {
        if (!::dataSourceFactory.isInitialized) {
            val upstreamFactory = DefaultDataSource.Factory(context, getHttpDataSourceFactory())
            dataSourceFactory = buildReadOnlyCacheDataSource(upstreamFactory, getDownloadCache(context))
        }

        return dataSourceFactory
    }

    fun getDownloadManager(context: Context): DownloadManager {
        ensureDownloadManagerInitialized(context)
        return downloadManager
    }

    private fun getDownloadCache(context: Context): Cache {
        if (!::downloadCache.isInitialized) {
            val downloadContentDirectory = File(context.filesDir, "videos/").apply { mkdir() }
            downloadCache = SimpleCache(downloadContentDirectory, NoOpCacheEvictor(), getDatabaseProvider(context))
        }

        return downloadCache
    }

    private fun ensureDownloadManagerInitialized(context: Context) {
        if (!::downloadManager.isInitialized) {
            downloadManager = DownloadManager(
                context, getDatabaseProvider(context), getDownloadCache(context), getHttpDataSourceFactory(), Executors.newFixedThreadPool(6)
            )
        }
    }

    private fun getDatabaseProvider(context: Context): DatabaseProvider {
        if (!::databaseProvider.isInitialized) {
            databaseProvider = StandaloneDatabaseProvider(context)
        }
        return databaseProvider
    }

    private fun buildReadOnlyCacheDataSource(upstreamFactory: Factory, cache: Cache): CacheDataSource.Factory {
        return CacheDataSource.Factory()
            .setCache(cache)
            .setUpstreamDataSourceFactory(upstreamFactory)
            .setCacheWriteDataSinkFactory(null)
            .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)
    }

    fun getDownloadNotificationHelper(context: Context): DownloadNotificationHelper {
        if (!::downloadNotificationHelper.isInitialized) {
            downloadNotificationHelper = DownloadNotificationHelper(
                context,
                VideoDownloadService.DOWNLOAD_NOTIFICATION_CHANNEL_ID
            )
        }

        return downloadNotificationHelper
    }
}
