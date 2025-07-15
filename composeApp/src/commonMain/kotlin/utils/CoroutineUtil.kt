package utils

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
    exception.printStackTrace()
}

fun createSafeScope(): CoroutineScope {
    val job = SupervisorJob()
    return CoroutineScope(Dispatchers.Main + coroutineExceptionHandler + job)
}
