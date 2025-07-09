
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import config.AppRouter
import config.Global
import config.NavigationScreens
import kmp_mvp.composeapp.generated.resources.Res
import kmp_mvp.composeapp.generated.resources.app_name
import model.FabModel
import org.jetbrains.compose.resources.stringResource
import theme.component.AppLauncher


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

fun NavGraphBuilder.allScreens() {
    AppRouter.screens.keys.forEach { routeName ->
        composable(route = routeName) {
            AppRouter.screens[routeName]?.run { this() }
        }
    }
}

@Composable
fun App(
    appVM: AppViewModel = viewModel { AppViewModel() },
    navigator: NavHostController = rememberNavController()
) {
    navigator.addOnDestinationChangedListener(AppRouter.callback)

    val appState: HomeUiState by appVM.homeUIState.collectAsState()

    AppLauncher(
        Global.USING_MATERIAL3,
        isSystemInDarkTheme(),
        title = stringResource(Res.string.app_name),
        fab = FabModel(Icons.Default.Done) {
            //
        },
        bottomBar = { AppNavigationBar(navigator) }
    ) {
        // register all screens to NavHost
        NavHost(
            navController = navigator,
            startDestination = NavigationScreens.Home.name,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(it),
            builder = { this.allScreens() }
        )
    }
}