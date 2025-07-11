package data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Long = 0,
    @SerialName("name")
    var name: String,
    @SerialName("full_name")
    var fullName: String,
) {
    companion object {
        fun empty(): User {
            return User(0, "", "")
        }
    }
}