package ui.screen

import config.AppRouter
import config.NavigationScreens

/**
 * register other new screens
 */
fun registerScreens() {
    registerNavigationScreens()
}

/**
 * register navigation screens
 */
private fun registerNavigationScreens() {
    NavigationScreens.entries.forEach {
       AppRouter.register(it.name) { it.creator() }
    }
}
