package data.model

import androidx.compose.ui.graphics.vector.ImageVector
import typing.Callback

sealed class ClickableIcon(
    open val icon: ImageVector,
    open val onClick: Callback,
    open val contentDescriptor: String = "",
)

data class MenuModel(
    override val icon: ImageVector,
    override val onClick: Callback,
) : ClickableIcon(icon, onClick, "")

data class FabModel(
    override val icon: ImageVector,
    override val onClick: Callback,
) : ClickableIcon(icon, onClick, "")