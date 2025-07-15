package ui.screen.player

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.FastRewind
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import kmp_mvp.composeapp.generated.resources.Res
import kmp_mvp.composeapp.generated.resources.back
import kmp_mvp.composeapp.generated.resources.forward
import kmp_mvp.composeapp.generated.resources.forward_5_sec
import kmp_mvp.composeapp.generated.resources.pause
import kmp_mvp.composeapp.generated.resources.play
import kmp_mvp.composeapp.generated.resources.rewind_5_sec
import org.jetbrains.compose.resources.stringResource
import ui.theme.accentColor
import ui.theme.borderRadiusSmall
import ui.theme.iconSizeMedium
import ui.theme.loadingOverlayColor
import ui.theme.playerBackgroundColor
import ui.theme.spacingMedium
import ui.theme.spacingSmall
import ui.theme.textColor

@Composable
internal fun KKPlayer(viewModel: PlayerViewModel) {
    viewModel.syncWithMediaPlayer()

    val state = viewModel.playerViewState.collectAsState()
    val trackList = state.value.trackList
    val isPlaying = state.value.isPlaying
    val currentTrackId = state.value.playingTrackId
    val isBuffering = state.value.isBuffering
    val isError = state.value.errorState

    if (trackList.isEmpty()) return

    val currentIndex = viewModel.getCurrentTrackIndex()
    val currentTrack = if (currentIndex >= 0) trackList[currentIndex] else null

    LaunchedEffect(isError) {
        if (isError) {
            viewModel.setBuffering(true)
            viewModel.playNextTrack()
        }
    }

    LaunchedEffect(currentTrackId) {
        if (currentTrackId.isNotEmpty()) {
            // onOutPut(PlayerComponent.Output.OnTrackUpdated(currentTrackId))
        }
    }

    Box(
        modifier = Modifier.fillMaxWidth().background(playerBackgroundColor)
            .padding(spacingMedium).clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }) { }) {

        Row(modifier = Modifier.fillMaxWidth()) {
//            val painter = rememberImagePainter(
//                url = currentTrack.albumImageUrl,
//            )
            Box(
                modifier = Modifier.clip(RoundedCornerShape(borderRadiusSmall)).width(49.dp)
                    .height(49.dp)
            ) {
                Image(
                    // painter = painter,
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = currentTrack?.albumImageUrl,
                    modifier = Modifier.clip(RoundedCornerShape(borderRadiusSmall)).width(49.dp)
                        .height(49.dp),
                    contentScale = ContentScale.Crop
                )
                if (isBuffering) {
                    Box(modifier = Modifier.fillMaxSize().background(loadingOverlayColor)) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center).padding(spacingSmall),
                            color = accentColor,
                        )
                    }
                }
            }
            Column(Modifier.weight(1f).padding(start = spacingSmall).align(Alignment.Top)) {
                Text(
                    text = currentTrack?.title?:"", style = MaterialTheme.typography.titleSmall.copy(
                        color = textColor
                    ),
                    modifier = Modifier.fillMaxWidth().basicMarquee(Int.MAX_VALUE)
                )
                Text(
                    text = currentTrack?.artist?:"",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = textColor
                    ),
                    modifier = Modifier.padding(top = spacingSmall)
                )
            }
            Row(modifier = Modifier.align(Alignment.CenterVertically)) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    tint = accentColor,
                    contentDescription = stringResource(Res.string.back),
                    modifier = Modifier.padding(end = spacingSmall).size(iconSizeMedium)
                        .align(Alignment.CenterVertically)
                        .clickable(onClick = {
                            viewModel.setBuffering(true)
                            viewModel.playPreviousTrack()
                        })
                )
                Icon(
                    //painter = painterResource(Res.drawable.rewind),
                    imageVector = Icons.Filled.FastRewind,
                    tint = accentColor,
                    contentDescription = stringResource(Res.string.rewind_5_sec),
                    modifier = Modifier
                        .padding(end = spacingSmall)
                        .size(iconSizeMedium)
                        .align(Alignment.CenterVertically)
                        .clickable(onClick = {
                            viewModel.rewind5Seconds()
                        })
                )
                PlayPauseButton(
                    modifier = Modifier.padding(end = spacingSmall).size(iconSizeMedium)
                        .align(Alignment.CenterVertically),
                    isPlaying = isPlaying,
                    onTogglePlayPause = { viewModel.togglePlayPause() }
                )
                Icon(
                    //painter = painterResource(Res.drawable.forward),
                    imageVector = Icons.Filled.FastForward,
                    tint = accentColor,
                    contentDescription = stringResource(Res.string.forward_5_sec),
                    modifier = Modifier
                        .padding(end = spacingSmall)
                        .size(iconSizeMedium)
                        .align(Alignment.CenterVertically)
                        .clickable(onClick = {
                            viewModel.forward5Seconds()
                        })
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    tint = accentColor,
                    contentDescription = stringResource(Res.string.forward),
                    modifier = Modifier.padding(end = spacingSmall).size(iconSizeMedium)
                        .align(Alignment.CenterVertically)
                        .clickable(onClick = {
                            viewModel.setBuffering(true)
                            viewModel.playNextTrack()
                        })
                )
            }
        }
    }
}

@Composable
fun PlayPauseButton(
    modifier: Modifier,
    isPlaying: Boolean,
    onTogglePlayPause: () -> Unit
) {
    if (isPlaying) Icon(
        imageVector = Icons.Filled.Pause,
        tint = accentColor,
        contentDescription = stringResource(Res.string.pause),
        modifier = modifier.clickable(onClick = onTogglePlayPause)
    ) else Icon(
        imageVector = Icons.Filled.PlayArrow,
        tint = accentColor,
        contentDescription = stringResource(Res.string.play),
        modifier = modifier.clickable(onClick = onTogglePlayPause)
    )
}