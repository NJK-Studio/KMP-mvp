package ui.theme.component

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import data.model.ClickableIcon
import data.model.MenuModel
import ui.theme.AppTheme

@Composable
fun AppLauncher(
    usingMaterial3: Boolean = false,
    darkTheme: Boolean = isSystemInDarkTheme(),
    title: String,
    fab: ClickableIcon?,
    bottomBar: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit,
) {
    AppTheme(darkTheme, true) {
        Scaffold(
            modifier = Modifier.padding(0.dp),
            topBar = { SimpleAppbar<MenuModel>(title, navigateBack = false) },
            bottomBar = bottomBar,
            snackbarHost = { },
            floatingActionButton = {
                fab?.let {
                    FloatingActionButton(onClick = it.onClick) {
                        Icon(
                            it.icon,
                            it.contentDescriptor
                        )
                    }
                }
            },
            floatingActionButtonPosition = FabPosition.End,
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = contentColorFor(MaterialTheme.colorScheme.background),
            contentWindowInsets = ScaffoldDefaults.contentWindowInsets,
        ) {
            content(it)
        }
    }
}