package io.github.juevigrace.diva.ui.navigation

import androidx.compose.ui.graphics.painter.Painter
import androidx.navigation3.runtime.NavKey

interface Tab<D : NavKey> {
    val route: D
    val icon: Painter
    val title: String
}
