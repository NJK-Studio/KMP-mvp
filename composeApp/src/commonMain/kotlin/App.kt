import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import config.AppRouter
import ui.KmpApp
import ui.rememberNiaAppState
import ui.theme.AppTheme


@Composable
fun App() {
    val appVM: AppViewModel = viewModel { AppViewModel() }

    val state = rememberNiaAppState()
    state.navController.addOnDestinationChangedListener(AppRouter.callback)

    LaunchedEffect(Unit) {
        // 加载配置
    }

    CompositionLocalProvider() {
        AppTheme(true, true) {
            KmpApp(state)
        }
    }
}
