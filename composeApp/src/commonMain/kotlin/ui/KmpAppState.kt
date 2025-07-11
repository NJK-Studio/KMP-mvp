package ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import kotlinx.coroutines.CoroutineScope
import navigation.TopLevelDestination

@Composable
fun rememberNiaAppState(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
): KmpAppState {
    return remember(
        navController,
        coroutineScope,
    ) {
        KmpAppState(
            navController = navController,
            coroutineScope = coroutineScope,
        )
    }
}

@Stable
class KmpAppState(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope,
) {
    private val previousDestination = mutableStateOf<NavDestination?>(null)

    val currentDestination: NavDestination?
        @Composable get() {
            // Collect the currentBackStackEntryFlow as a state
            val currentEntry = navController.currentBackStackEntryFlow
                .collectAsState(initial = null)

            // Fallback to previousDestination if currentEntry is null
            return currentEntry.value?.destination.also { destination ->
                if (destination != null) {
                    previousDestination.value = destination
                }
            } ?: previousDestination.value
        }

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() {
            return TopLevelDestination.entries.firstOrNull { topLevelDestination ->
                currentDestination?.hasRoute(route = topLevelDestination.route.simpleName!!, null) == true
            }
        }

    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.entries

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        val topLevelNavOptions = navOptions {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().navigatorName) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // re selecting the same item
            launchSingleTop = true
            // Restore state when re selecting a previously selected item
            restoreState = true
        }

//        when (topLevelDestination) {
//            TopLevelDestination.HOME -> navController.navigateToForYou(topLevelNavOptions)
//            TopLevelDestination.SUMMARY -> navController.navigateToBookmarks(topLevelNavOptions)
//            TopLevelDestination.SETTINGS -> navController.navigateToInterests(null, topLevelNavOptions)
//        }
    }
}
