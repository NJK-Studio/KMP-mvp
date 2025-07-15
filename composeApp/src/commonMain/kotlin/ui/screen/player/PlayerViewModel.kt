package ui.screen.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import model.TrackItem
import player.MediaPlayerController
import player.MediaPlayerListener
import player.SEEK_TO_SECONDS

class PlayerViewModel(
    private val media: MediaPlayerController,
    trackList: List<TrackItem>,
    selectedTrack: String,
) : ViewModel() {

    val playerViewState = MutableStateFlow(
        PlayerViewState(
            trackList = trackList,
            playingTrackId = selectedTrack,
            isPlaying = false
        )
    )

    private val mediaPlayerListener = object : MediaPlayerListener {
        override fun onReady() {
            updatePlayerState {
                it.copy(
                    isBuffering = false,
                    errorState = false
                )
            }
            media.start()
        }

        override fun onAudioCompleted() {
            updatePlayerState {
                it.copy(
                    isPlaying = false
                )
            }
        }

        override fun onError() {
            updatePlayerState {
                it.copy(
                    errorState = true,
                    isBuffering = false
                )
            }
        }

        override fun onTrackChanged(trackId: String) {
            updatePlayerState {
                it.copy(
                    playingTrackId = trackId,
                    errorState = false
                )
            }
        }

        override fun onBufferingStateChanged(isBuffering: Boolean) {
            updatePlayerState {
                it.copy(
                    isBuffering = isBuffering
                )
            }
        }

        override fun onPlaybackStateChanged(isPlaying: Boolean) {
            updatePlayerState {
                it.copy(
                    isPlaying = isPlaying
                )
            }
        }
    }

    private fun updatePlayerState() {
        val currentTrack = media.getCurrentTrack() ?: return
        val currentPosition = media.getCurrentPosition() ?: 0L
        val duration = media.getDuration()
        val isPlaying = media.isPlaying()

        val newState = playerViewState.value.copy(
            playingTrackId = currentTrack.id,
            currentPosition = currentPosition,
            duration = duration,
            isPlaying = isPlaying,
            errorState = false
        )
        playerViewState.value = newState
    }

    private fun updatePlayerState(transform: (PlayerViewState) -> PlayerViewState) {
        playerViewState.value = transform(playerViewState.value)
    }

    fun syncWithMediaPlayer() {
        updatePlayerState()
    }

    fun playTrack(trackId: String) {
        val track = playerViewState.value.trackList.find { it.id == trackId } ?: return
        updatePlayerState {
            it.copy(
                isBuffering = true
            )
        }
        media.prepare(track, mediaPlayerListener)
    }

    fun togglePlayPause() {
        if (media.isPlaying()) {
            media.pause()
        } else {
            media.start()
        }
        updatePlayerState()
    }

    fun playNextTrack() {
        if (media.playNextTrack()) {
            updatePlayerState()
        }
    }

    fun playPreviousTrack() {
        if (media.playPreviousTrack()) {
            updatePlayerState()
        }
    }

    fun getCurrentTrackIndex(): Int {
        val currentTrackId = playerViewState.value.playingTrackId
        return playerViewState.value.trackList.indexOfFirst { it.id == currentTrackId }
    }

    init {
        viewModelScope.launch {
//            playerInputs.collectLatest {
//                when (it) {
//                    is PlayerComponent.Input.PlayTrack -> {
//                        val newState = playerViewState.value.copy(
//                            playingTrackId = it.trackId,
//                            trackList = it.tracksList
//                        )
//                        playerViewState.value = newState
//
//                        media.setTrackList(it.tracksList, it.trackId)
//                        playTrack(it.trackId)
//                    }
//
//                    is PlayerComponent.Input.UpdateTracks -> {
//                        val newState = playerViewState.value.copy(trackList = it.tracksList)
//                        playerViewState.value = newState
//
//                        media.setTrackList(it.tracksList, newState.playingTrackId)
//                    }
//                }
//            }
        }

        media.setTrackList(trackList, selectedTrack)

        if (selectedTrack.isNotEmpty()) {
            playTrack(selectedTrack)
        }
    }

    fun rewind5Seconds() {
        media.getCurrentPosition()?.let {
            (it - SEEK_TO_SECONDS).coerceAtLeast(0).let(media::seekTo)
        }
        updatePlayerState()
    }

    fun forward5Seconds() {
        media.getCurrentPosition()?.let { currentPosition ->
            media.getDuration()?.let { duration ->
                (currentPosition + SEEK_TO_SECONDS).coerceAtMost(duration)
                    .let(media::seekTo)
            }
        }
        updatePlayerState()
    }

    fun setBuffering(isBuffering: Boolean) {
        val newState = playerViewState.value.copy(
            isBuffering = isBuffering
        )
        playerViewState.value = newState
    }

    fun onDestroy() {
        viewModelScope.cancel()
    }
}