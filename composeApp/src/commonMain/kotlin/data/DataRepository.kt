package data

import data.store.UserDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import model.User

class DataRepository(
//    private val api: UserApi,
    private val userStore: UserDataStore,
    private val scope: CoroutineScope,
) {
    suspend fun updateUserProfile(user: User) {
        userStore.update(user)
    }

    fun getUserProfile(): Flow<User> {
        return userStore.user
    }

//    fun getData(): Flow<List<User>> {
//        scope.launch {
//            if (database.userDao().count() < 1) {
//                refreshData()
//            }
//        }
//        return loadData()
//    }
//
//    fun loadData(): Flow<List<User>> {
//        return database.fruittieDao().getAllAsFlow()
//    }
//
    suspend fun fetchUser(userName:String){
//        val remoteUser = api.getUserBy(userName)
//        userStore.add(remoteUser)
//        database.fruittieDao().insert(response.feed)
    }

}