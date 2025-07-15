
@Suppress(names = ["EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING"])
actual class PlatformContext
actual fun getPlatformContext(): PlatformContext {
   return PlatformContext()
}

/**
 * 解析不同平台文件路径
 */
actual fun resolveSystemFilePath(filePath: String): String {
   return "~/"
}