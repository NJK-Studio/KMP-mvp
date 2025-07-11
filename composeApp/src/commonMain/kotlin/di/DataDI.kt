package di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import data.store.AppStore
import data.store.UserDataStore
import api.resolveSystemFilePath

class DataDI {
    //    fun createRoomDatabase(): AppDatabase
    /**
     * create http api request
     */
//    fun createApi(): UserApi {
//        return UserNetworkApi(
//            client = KtorFactory.create(),
//            apiUrl = Global.API_URL,
//        )
//    }

    /**
     * custom data store
     */
    fun createUserDataStore(): UserDataStore {
        return UserDataStore { resolveSystemFilePath("user.json") }
    }

    /**
     * App key-value storage
     */
    fun createAppStore(): DataStore<Preferences> {
        return AppStore.get()
    }

}