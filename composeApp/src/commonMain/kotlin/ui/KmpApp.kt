package ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.navigation.NavHostController
import config.NavigationScreens
import navigation.AppNavHost
import navigation.KmpNavigationSuiteScaffold
import org.jetbrains.compose.resources.stringResource
import ui.theme.AppBackground
import ui.theme.KIcons
import ui.theme.component.KiaTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KmpApp(
    appState: KmpAppState,
    modifier: Modifier = Modifier,
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo(),
) {
    AppBackground(modifier) {
        val currentDestination = appState.currentDestination
        val snackbarHostState = remember { SnackbarHostState() }

        KmpNavigationSuiteScaffold(
            navigationSuiteItems = {
                appState.topLevelDestinations.forEach { destination ->
                    // val hasUnread = unreadDestinations.contains(destination)
                    val selected = false
                    //currentDestination.isRouteInHierarchy(destination.baseRoute)
                    item(
                        selected = selected,
                        onClick = { appState.navigateToTopLevelDestination(destination) },
                        icon = {
                            Icon(
                                imageVector = destination.unselectedIcon,
                                contentDescription = null,
                            )
                        },
                        selectedIcon = {
                            Icon(
                                imageVector = destination.selectedIcon,
                                contentDescription = null,
                            )
                        },
                        label = { Text(stringResource(destination.titleTextId)) },
                        modifier = Modifier
                        // .then(if (hasUnread) Modifier.notificationDot() else Modifier),
                    )
                }
            },
            windowAdaptiveInfo = windowAdaptiveInfo,
        ) {
            Scaffold(
                modifier = modifier.semantics {
                    // testTagsAsResourceId = true
                },
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.onBackground,
                contentWindowInsets = WindowInsets(0, 0, 0, 0),
                snackbarHost = { SnackHost(snackbarHostState) },
            ) { padding ->
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .consumeWindowInsets(padding)
                        .windowInsetsPadding(
                            WindowInsets.safeDrawing.only(
                                WindowInsetsSides.Horizontal,
                            ),
                        ),
                ) {
                    // Show the top app bar on top level destinations.
                    val destination = appState.currentTopLevelDestination
                    var shouldShowTopAppBar = false

                    if (destination != null) {
                        shouldShowTopAppBar = true
                        KiaTopAppBar(
                            titleRes = destination.titleTextId,
                            navigationIcon = KIcons.Search,
                            navigationIconContentDescription = "",
                            actionIcon = KIcons.Settings,
                            actionIconContentDescription = "",
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Color.Transparent,
                            ),
                            onActionClick = { },
                            onNavigationClick = { },
                        )
                    }

                    Box(
                        // Workaround for https://issuetracker.google.com/338478720
                        modifier = Modifier.consumeWindowInsets(
                            if (shouldShowTopAppBar) {
                                WindowInsets.safeDrawing.only(WindowInsetsSides.Top)
                            } else {
                                WindowInsets(0, 0, 0, 0)
                            },
                        ),
                    ) {
                        AppNavHost(appState)
                    }
                }
            }
        }
    }
}


@Composable
fun SnackHost(state: SnackbarHostState) {
    SnackbarHost(
        state,
        modifier = Modifier.windowInsetsPadding(
            WindowInsets.safeDrawing.exclude(
                WindowInsets.ime,
            ),
        ),
    )
}


@Composable
fun AppNavigationBar(navigator: NavHostController) {
    var selectedItem by remember { mutableIntStateOf(0) }

    NavigationBar {
        NavigationScreens.entries.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(item.icon, item.label) },
                label = { Text(item.label) },
                selected = selectedItem == index,
                onClick = {
                    if (navigator.currentDestination?.route == item.name) {
                        return@NavigationBarItem
                    }
                    selectedItem = index
                    navigator.navigate(item.name)
                }
            )
        }
    }
}
