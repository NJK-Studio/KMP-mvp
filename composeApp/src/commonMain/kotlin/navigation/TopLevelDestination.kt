package navigation

import androidx.compose.ui.graphics.vector.ImageVector
import kmp_mvp.composeapp.generated.resources.Res
import kmp_mvp.composeapp.generated.resources.home
import kmp_mvp.composeapp.generated.resources.settings
import kmp_mvp.composeapp.generated.resources.summary
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.StringResource
import ui.theme.KIcons
import kotlin.reflect.KClass

enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: StringResource,
    val titleTextId: StringResource,
    val route: KClass<*>,
    val baseRoute: KClass<*> = route,
) {
    HOME(
        selectedIcon = KIcons.Upcoming,
        unselectedIcon = KIcons.UpcomingBorder,
        iconTextId = Res.string.home,
        titleTextId = Res.string.home,
        route = HomeRoute::class,
        baseRoute = HomeBaseRoute::class,
    ),
    SUMMARY(
        selectedIcon = KIcons.Bookmarks,
        unselectedIcon = KIcons.BookmarksBorder,
        iconTextId =  Res.string.summary,
        titleTextId = Res.string.summary,
        route = SummaryRoute::class,
    ),
    SETTINGS(
        selectedIcon = KIcons.Grid3x3,
        unselectedIcon = KIcons.Grid3x3,
        iconTextId = Res.string.settings,
        titleTextId = Res.string.settings,
        route = SettingsRoute::class,
    ),
}

// route to base navigation graph
@Serializable data object HomeBaseRoute
// route to Home screen
@Serializable data object HomeRoute
@Serializable data object SummaryRoute
@Serializable data object SettingsRoute
