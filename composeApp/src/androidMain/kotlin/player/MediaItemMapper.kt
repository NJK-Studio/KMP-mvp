package player

import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import model.TrackItem

fun TrackItem.toMediaItem(): MediaItem {
    val metadata = MediaMetadata.Builder()
        .setTitle(title)
        .setArtist(artist)
        .setArtworkUri(albumImageUrl.toUri())
        .build()

    return MediaItem.Builder()
        .setMediaId(pathSource)
        .setUri(pathSource)
        .setMediaMetadata(metadata)
        .build()
}