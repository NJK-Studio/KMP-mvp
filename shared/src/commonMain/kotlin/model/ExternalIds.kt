package model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExternalIds(
    @SerialName("isrc")
    val isrc: String?
)