package data.store

import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.okio.OkioSerializer
import androidx.datastore.core.okio.OkioStorage
import data.http.json
import kotlinx.coroutines.flow.Flow
import data.model.User
import okio.BufferedSink
import okio.BufferedSource
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.SYSTEM
import okio.use

/**
 * 自定义数据存储
 */
class UserDataStore(private val produceFilePath: () -> String) {
    private val db = DataStoreFactory.create(
        storage = OkioStorage(
            fileSystem = FileSystem.SYSTEM,
            serializer = UserJsonSerializer,
            producePath = { produceFilePath().toPath() },
        ),
    )
    val user: Flow<User> get() = db.data
    suspend fun add(user: User) = update(user)
    suspend fun remove(user: User) = update(user)
    suspend fun update(user: User) {
        db.updateData { oUser ->

            user
        }
    }
}

internal object UserJsonSerializer : OkioSerializer<User> {
    override val defaultValue: User = User.empty()
    override suspend fun readFrom(source: BufferedSource): User {
        return json.decodeFromString<User>(source.readUtf8())
    }

    override suspend fun writeTo(t: User, sink: BufferedSink) {
        sink.use {
            it.writeUtf8(json.encodeToString(User.serializer(), t))
        }
    }
}
