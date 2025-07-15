import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable


class DesktopThemeExt : PlatformTheme {
    @Composable
    override fun CustomColorScheme(darkTheme: Boolean, dynamicColor: Boolean): ColorScheme? {
        return null
    }

}

actual fun getThemeExt(): PlatformTheme = DesktopThemeExt()