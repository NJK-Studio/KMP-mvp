package ui.screen.player

import model.TrackItem

data class PlayerViewState(
    val trackList: List<TrackItem>,
    val playingTrackId: String = "",
    val currentPosition: Long = 0,
    val isPlaying: Boolean = false,
    val duration: Long? = null,
    val isBuffering: Boolean = false,
    val errorState: Boolean = false
)