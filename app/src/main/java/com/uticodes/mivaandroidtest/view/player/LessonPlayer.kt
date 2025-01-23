package com.uticodes.mivaandroidtest.view.player

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.ui.PlayerView
import com.github.fengdai.compose.media.MediaState

@Composable
@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
fun LessonPlayer(
    mediaState: MediaState,
    modifier: Modifier = Modifier
) {
    AndroidView(
        factory = { context ->
            PlayerView(context).apply {
                hideController()
                setShowPreviousButton(false)
                setShowNextButton(false)
                setShowBuffering(PlayerView.SHOW_BUFFERING_ALWAYS)
                setShowShuffleButton(false)
                setShowVrButton(false)
                setShowSubtitleButton(true)
            }
        },
        modifier = modifier
    ) {
        it.player = mediaState.player
    }
}
