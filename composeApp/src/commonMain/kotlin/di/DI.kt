package di

import data.DataRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

/**
 * 容器注入
 */
object DI {
    private val factory: DataDI by lazy { DataDI() }

    val dataRepo: DataRepository by lazy {
        DataRepository(
            //api = factory.createApi(),
            userStore = factory.createUserDataStore(),
            scope = CoroutineScope(Dispatchers.Default + SupervisorJob()),
        )
    }
}
