package navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import config.AppRouter
import config.NavigationScreens
import ui.KmpAppState

@Composable
fun AppNavHost(
    state: KmpAppState,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = state.navController,
        startDestination = NavigationScreens.Home.name,
        modifier = modifier,
        builder = { allScreens() }
    )
}

fun NavGraphBuilder.allScreens() {
    AppRouter.screens.keys.forEach { routeName ->
        composable(route = routeName) {
            AppRouter.screens[routeName]?.run { this() }
        }
    }
}
