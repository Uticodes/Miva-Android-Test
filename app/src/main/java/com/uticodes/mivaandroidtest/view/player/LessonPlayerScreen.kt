package com.uticodes.mivaandroidtest.view.player

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.fengdai.compose.media.Media
import com.github.fengdai.compose.media.MediaState
import com.github.fengdai.compose.media.ShowBuffering
import com.github.fengdai.compose.media.rememberMediaState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.uticodes.mivaandroidtest.R
import com.uticodes.mivaandroidtest.ui.theme.TextColor
import com.uticodes.mivaandroidtest.utils.LessonsWrapper
import com.uticodes.mivaandroidtest.utils.findActivity
import com.uticodes.mivaandroidtest.utils.shareContent
import com.uticodes.mivaandroidtest.utils.showToast

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Destination(
    navArgsDelegate = LessonPlayerScreenNavArgs::class
)
@Composable
fun LessonPlayerScreen(navigator: DestinationsNavigator) {
    val viewModel: LessonPlayerScreenViewModel = hiltViewModel()
    val loading by remember { viewModel.loading }
    var showBookmarkDialog by remember { viewModel.showBookmarkDialog }
    var temporarilyPaused by remember { viewModel.temporarilyPaused }
    var dialogText by remember { mutableStateOf("") }
    val bookmarks = remember { viewModel.bookmarks }
    val currentLesson by remember { viewModel.currentLesson }

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    val context = LocalContext.current

    val shareContent = "Check out this amazing lesson! \nTitle: ${currentLesson.title}, " +
        "\n Install Miva app to watch it: " +
        "https://play.google.com/store/search?q=UlESSON&c=apps&hl=en&gl=US "

    BackHandler(enabled = true) {
        viewModel.saveCurrentLesson()
        navigator.popBackStack()
    }

    val mediaState = rememberMediaState(viewModel.exoPlayer)
    val mediaContent = remember {
        movableContentOf { isLandscape: Boolean, modifier: Modifier ->
            MediaContent(mediaState, isLandscape, modifier)
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { navigator.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
                    }
                },
                modifier = Modifier
                    .semantics { testTagsAsResourceId = true }
                    .testTag("topAppBar")
            )
        }
    ) { padding ->
        if (!isLandscape) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                mediaContent(
                    false,
                    Modifier
                        .padding(padding)
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Icon(
                        modifier = Modifier.clickable {
                            showBookmarkDialog = true
                            temporarilyPaused = viewModel.exoPlayer.isPlaying
                            viewModel.exoPlayer.pause()
                        },
                        painter = painterResource(R.drawable.ic_bookmark),
                        contentDescription = "Bookmark",
                        tint = Color(0xFFEA7052)
                    )
                    Icon(
                        modifier = Modifier.clickable {
                            temporarilyPaused = viewModel.exoPlayer.isPlaying
                            viewModel.exoPlayer.pause()
                            context.shareContent(
                                content = shareContent
                            )
                        },
                        painter = painterResource(R.drawable.ic_share),
                        contentDescription = "Share",
                        tint = Color(0xFFEA7052)
                    )
                }
                Text(
                    currentLesson.title,
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = TextColor
                    ),
                    modifier = Modifier.padding(12.dp)
                )

                BookmarkList(
                    modifier = Modifier.weight(1f),
                    bookmarks = bookmarks,
                    onSelectBookmark = { bookmark ->
                        viewModel.exoPlayer.seekTo(bookmark.timestamp)
                    },
                    onDeleteBookmark = { bookmark ->
                        viewModel.deleteBookmark(bookmark)
                    }
                )
            }
        }
    }

    if (isLandscape) {
        mediaContent(
            true,
            Modifier
                .fillMaxSize()
                .background(Color.Black)
        )
    }

    if (loading) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }

    if (showBookmarkDialog) {
        AlertDialog(
            onDismissRequest = {
                showBookmarkDialog = false
                if (temporarilyPaused) {
                    viewModel.exoPlayer.play()
                    temporarilyPaused = false
                }
            },
            title = { Text("Add Notes") },
            text = {
                Column {
                    TextField(
                        value = dialogText,
                        onValueChange = { dialogText = it },
                        maxLines = 4,
                        colors = TextFieldDefaults.colors(
                            errorIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                        ),
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (dialogText.trim().isEmpty()) {
                            context.showToast(context.getString(R.string.kindly_enter_a_note))
                            return@TextButton
                        }
                        viewModel.saveBookmark(dialogText.trim())

                        showBookmarkDialog = false
                        if (temporarilyPaused) {
                            viewModel.exoPlayer.play()
                            temporarilyPaused = false
                        }
                    }
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showBookmarkDialog = false
                        if (temporarilyPaused) {
                            viewModel.exoPlayer.play()
                            temporarilyPaused = false
                        }
                    }
                ) {
                    Text("Cancel".uppercase())
                }
            },
        )
    }
}

@Composable
private fun MediaContent(
    mediaState: MediaState,
    isLandscape: Boolean,
    modifier: Modifier = Modifier
) {
    val activity = LocalContext.current.findActivity()!!
    val enterFullscreen = {
        activity.requestedOrientation =
            ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE
    }
    val exitFullscreen = {
        @SuppressLint("SourceLockedOrientationActivity")
        // Will reset to SCREEN_ORIENTATION_USER later
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT
    }
    Box(modifier) {
        Media(
            mediaState,
            modifier = Modifier.fillMaxSize(),
            showBuffering = ShowBuffering.Always,
            buffering = {
                Box(Modifier.fillMaxSize(), Alignment.Center) {
                    CircularProgressIndicator()
                }
            },
            controller = { LessonPlayer(mediaState, Modifier.fillMaxSize()) }
        )
        Button(
            modifier = Modifier.align(Alignment.BottomEnd),
            onClick = if (isLandscape) exitFullscreen else enterFullscreen,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            )
        ) {
            Icon(
                painter = painterResource(if (isLandscape) R.drawable.ic_fullscreen_exit else R.drawable.ic_full_screen),
                contentDescription = "Fullscreen/Exit",
                tint = Color.White,
            )
        }
    }
    val onBackPressedCallback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                exitFullscreen()
            }
        }
    }
    val onBackPressedDispatcher = activity.onBackPressedDispatcher
    DisposableEffect(onBackPressedDispatcher) {
        onBackPressedDispatcher.addCallback(onBackPressedCallback)
        onDispose { onBackPressedCallback.remove() }
    }
    SideEffect {
        onBackPressedCallback.isEnabled = isLandscape
        if (isLandscape) {
            if (activity.requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_USER) {
                activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE
            }
        } else {
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_USER
        }
    }
}

data class LessonPlayerScreenNavArgs(
    val lessons: LessonsWrapper,
    val index: Int
)
