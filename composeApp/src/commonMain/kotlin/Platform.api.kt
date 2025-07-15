@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

expect class PlatformContext
expect fun getPlatformContext(): PlatformContext

/**
 * 解析不同平台文件路径
 */
expect fun resolveSystemFilePath(filePath: String): String