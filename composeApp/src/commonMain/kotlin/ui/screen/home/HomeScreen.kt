package ui.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun HomeScreen(
    appVM: HomeViewModel = viewModel { HomeViewModel() },
    modifier: Modifier = Modifier
) {
    Column {

        Text("Home")
        Text("Home")
        Text("Home")
    }
}
