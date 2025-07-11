package data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * base api response data list
 */
@Serializable
data class ListResponse<T>(
    @SerialName("data")
    val data: List<T>,
    @SerialName("totalPages")
    val totalPages: Int,
    @SerialName("currentPage")
    val currentPage: Int = 0,
    @SerialName("pageSize")
    val pageSize: Int = 20,
)
