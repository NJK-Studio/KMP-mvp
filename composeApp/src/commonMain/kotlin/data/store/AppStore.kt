package data.store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import config.Global
import kotlinx.atomicfu.locks.SynchronizedObject
import kotlinx.atomicfu.locks.synchronized
import okio.Path.Companion.toPath
import resolveSystemFilePath


/**
 * 基础数据类型 key-value 存储
 */
object AppStore {
    private lateinit var dataStore: DataStore<Preferences>

    private val lock = SynchronizedObject()
    private val producePath: () -> String = { resolveSystemFilePath(DATA_STORE_FILE_NAME) }

    /**
     * Gets the singleton DataStore instance, creating it if necessary.
     */
    fun get(): DataStore<Preferences> = synchronized(lock) {
        if (AppStore::dataStore.isInitialized) {
            dataStore
        } else {
            PreferenceDataStoreFactory.createWithPath(produceFile = { producePath().toPath() })
                .also { dataStore = it }
        }
    }

    private const val DATA_STORE_FILE_NAME = Global.DATA_STORE_SCHEMA

}