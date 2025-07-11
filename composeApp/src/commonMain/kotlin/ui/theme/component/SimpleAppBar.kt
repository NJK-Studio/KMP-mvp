package ui.theme.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import data.model.ClickableIcon

/**
 * 通用 appbar
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T : ClickableIcon> SimpleAppbar(
    title: String,
    menus: List<T>? = null,
    navigateBack: Boolean = true,
) {
    TopAppBar(
        title = { Text(title) },
        modifier = Modifier,
        navigationIcon = {
            if (navigateBack) {
                IconButton({
                    println("TODO go back")
                }) {
                    Icon(Icons.AutoMirrored.Default.ArrowBack, "Go back")
                }
            }
        },
        actions = {
            menus?.map {
                IconButton(onClick = it.onClick) {
                    Icon(it.icon, it.contentDescriptor)
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(),
    )
}