
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import di.DI
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import data.model.User


/**
 * Application main viewmodel
 */
class AppViewModel() : ViewModel() {

    val homeUIState: StateFlow<HomeUiState> =
        DI.dataRepo.getUserProfile().map { HomeUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = HomeUiState()
            )
}

/**
 * Ui State for ListScreen
 */
data class HomeUiState(val user: User = User.empty())

private const val TIMEOUT_MILLIS = 5_000L
