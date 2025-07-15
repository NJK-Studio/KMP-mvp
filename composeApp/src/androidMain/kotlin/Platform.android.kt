import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.kmp.mvp.MVPApplication

actual class PlatformContext(val applicationContext: Context)

private var applicationContext: Context? = null

/**
 * Initialize the platform context with the application context.
 * This should be called from the Application class or MainActivity.
 */
fun initializePlatformContext(context: Context) {
    applicationContext = context.applicationContext
}

/**
 * Get the platform context for Android.
 * This returns a PlatformContext instance that wraps the Android application context.
 */
actual fun getPlatformContext(): PlatformContext {
    val context = applicationContext ?: throw IllegalStateException(
        "PlatformContext not initialized. Call initializePlatformContext() first."
    )
    return PlatformContext(context)
}

/**
 * Get the platform context from a Composable function.
 * This is an alternative way to get the platform context when in a Composable scope.
 */
@Composable
fun getPlatformContextFromComposable(): PlatformContext {
    val context = LocalContext.current
    return PlatformContext(context)
}

actual fun resolveSystemFilePath(filePath: String): String {
    return MVPApplication.mvp.currentContext!!.filesDir.resolve(filePath).absolutePath
}