package com.uticodes.mivaandroidtest.view.player

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.Uri
import android.net.wifi.WifiInfo
import android.os.Build
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.Player.MEDIA_ITEM_TRANSITION_REASON_AUTO
import androidx.media3.exoplayer.DefaultLoadControl
import androidx.media3.exoplayer.DefaultRenderersFactory
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.offline.DownloadRequest
import androidx.media3.exoplayer.source.ConcatenatingMediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import com.uticodes.mivaandroidtest.data.models.Bookmark
import com.uticodes.mivaandroidtest.data.models.Lesson
import com.uticodes.mivaandroidtest.data.remote.repository.download.AppDownloadManager
import com.uticodes.mivaandroidtest.usecases.BookmarkUseCase
import com.uticodes.mivaandroidtest.usecases.ResumeLearningUseCase
import com.uticodes.mivaandroidtest.utils.PlayerUtil
import com.uticodes.mivaandroidtest.view.destinations.LessonPlayerScreenDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
@HiltViewModel
class LessonPlayerScreenViewModel @Inject constructor(
    state: SavedStateHandle,
    private val bookmarkUseCase: BookmarkUseCase,
    private val downloadManager: AppDownloadManager,
    private val playerUtil: PlayerUtil,
    @ApplicationContext private val context: Context,
    private val resumeLearningUseCase: ResumeLearningUseCase
) : ViewModel() {
    private val navArgs = LessonPlayerScreenDestination.argsFrom(state)
    val exoPlayer: ExoPlayer by lazy {
        ExoPlayer
            .Builder(
                context,
                DefaultRenderersFactory(context)
                    .forceEnableMediaCodecAsynchronousQueueing()
            )
            .setLoadControl(DefaultLoadControl())
            .build()
    }

    private val lessons: List<Lesson>
        get() = navArgs.lessons.lessons

    val currentLesson = mutableStateOf(navArgs.lessons[navArgs.index])
    val currentLessonIndex = mutableIntStateOf(navArgs.index)

    val loading = mutableStateOf(false)
    val showBookmarkDialog = mutableStateOf(false)
    val temporarilyPaused = mutableStateOf(false)
    val bookmarks = mutableStateListOf<Bookmark>()

    init {
        fetchBookmarks()
        initializeMedia()
    }

    fun fetchBookmarks() {
        bookmarks.clear()
        bookmarks.addAll(bookmarkUseCase.getBookmarks(currentLesson.value))
    }

    private fun initializeMedia() {
        val savedProgress = resumeLearningUseCase.getSavedProgress()
        val mediaSources = navArgs.lessons.lessons.mapIndexed { index, lesson ->
            val uri = Uri.parse(lesson.videoUrl)
            if (index in (navArgs.index - 1)..(navArgs.index + 1) && isOnWiFi()) {
                downloadManager.queueDownload(DownloadRequest.Builder(uri.toString(), uri).build())
            }

            ProgressiveMediaSource.Factory(playerUtil.getDataSourceFactory(context))
                .createMediaSource(MediaItem.fromUri(uri))
        }

//        val source = ConcatenatingMediaSource(*mediaSources.toTypedArray())
        val concatenatingMediaSource = ConcatenatingMediaSource()
        mediaSources.forEach { mediaSource ->
            concatenatingMediaSource.addMediaSource(mediaSource)
        }
        exoPlayer.setMediaSource(concatenatingMediaSource)
        exoPlayer.prepare()
        var seekPosition = C.TIME_UNSET
        savedProgress?.apply {
            if (lesson == currentLesson.value) {
                exoPlayer.seekTo(currentLessonIndex.intValue, timestamp)
                seekPosition = timestamp
            }
        }
        exoPlayer.seekTo(currentLessonIndex.intValue, seekPosition)
        exoPlayer.playWhenReady = true
        downloadManager.resumeAllDownloads()

        exoPlayer.addListener(object : Player.Listener {
            override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                super.onMediaItemTransition(mediaItem, reason)

                if (reason == MEDIA_ITEM_TRANSITION_REASON_AUTO) {
                    resumeLearningUseCase.clearProgress()

                    if (currentLessonIndex.value + 1 < lessons.size) {
                        currentLessonIndex.value += 1
                        currentLesson.value = lessons[currentLessonIndex.value]

                        // Fetch updated bookmarks
                        fetchBookmarks()

                        navArgs.lessons.lessons.forEachIndexed { index, lesson ->
                            val uri = Uri.parse(lesson.videoUrl)
                            if (index in (navArgs.index - 1)..(navArgs.index + 1) && isOnWiFi()) {
                                downloadManager.queueDownload(DownloadRequest.Builder(uri.toString(), uri).build())
                            }
                        }
                    }
                }
            }

            override fun onPlaybackStateChanged(playbackState: Int) {
                super.onPlaybackStateChanged(playbackState)
            }
        })
    }

    fun isOnWiFi(): Boolean {
        val connectivityManager = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)?.transportInfo is WifiInfo
        }
        return connectivityManager.getNetworkInfo(connectivityManager.activeNetwork)?.type == ConnectivityManager.TYPE_WIFI
    }

    fun saveBookmark(note: String) {
        val currentTime = exoPlayer.currentPosition
        val savedLesson = currentLesson.value
        viewModelScope.launch {
            bookmarkUseCase.addBookmark(
                savedLesson,
                Bookmark(
                    note = note,
                    timestamp = currentTime
                )
            )
            fetchBookmarks()
        }
    }

    fun deleteBookmark(bookmark: Bookmark) {
        viewModelScope.launch {
            bookmarkUseCase.deleteBookmark(currentLesson.value, bookmark)
            fetchBookmarks()
        }
    }

    fun saveCurrentLesson() {
        if (exoPlayer.currentPosition < exoPlayer.duration) {
            resumeLearningUseCase.saveLessonProgress(currentLesson.value, exoPlayer.currentPosition)
        } else {
            resumeLearningUseCase.clearProgress()
        }
    }

    override fun onCleared() {
        super.onCleared()
        exoPlayer.release()
    }
}
