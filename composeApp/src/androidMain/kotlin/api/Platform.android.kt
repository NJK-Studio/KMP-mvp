package api

import com.kmp.mvp.MVPApplication

actual fun resolveSystemFilePath(filePath: String): String {
    return MVPApplication.mvp.currentContext!!.filesDir.resolve(filePath).absolutePath
}