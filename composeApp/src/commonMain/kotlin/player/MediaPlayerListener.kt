package player


const val SEEK_TO_SECONDS = 5000L

interface MediaPlayerListener {

    fun onReady()
    fun onAudioCompleted()
    fun onError()
    fun onTrackChanged(trackId: String)
    fun onBufferingStateChanged(isBuffering: Boolean) { /* Optional implementation */
    }

    fun onPlaybackStateChanged(isPlaying: Boolean) { /* Optional implementation */
    }
}