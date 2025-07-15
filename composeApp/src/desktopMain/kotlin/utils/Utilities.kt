package utils

import java.util.Locale

internal fun isMacPlatform(): Boolean {
    val os = System.getProperty("os.name", "generic").lowercase(Locale.ENGLISH)
    return os.contains("mac") || os.contains("darwin")
}