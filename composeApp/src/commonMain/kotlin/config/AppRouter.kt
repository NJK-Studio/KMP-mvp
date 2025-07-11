package config

import androidx.navigation.NavController
import androidx.navigation.NavDestination
import ui.screen.registerScreens

object AppRouter {
    // dynamic router for all pages
    val screens = mutableMapOf<RouteName, ScreenCreator>()

    init {
        registerScreens()
    }
    fun register(routeName: RouteName, creator: ScreenCreator) {
        if (screens.containsKey(routeName)) {
            return
        }

        screens[routeName] = creator
    }

    val callback =
        NavController.OnDestinationChangedListener { navController: NavController, navDestination: NavDestination, bundle: Any? ->
            println(" Navigate to ==> ${navDestination.route}")
        }

}