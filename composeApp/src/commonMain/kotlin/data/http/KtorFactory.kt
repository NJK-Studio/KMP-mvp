package data.http

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

val json = Json { ignoreUnknownKeys = true }

object KtorFactory {

    /**
     * create base http client
     */
    fun create(): HttpClient {
        val client = HttpClient {
            install(ContentNegotiation) {
                json(json, contentType = ContentType.Any)
            }
        }
        return client
    }
}