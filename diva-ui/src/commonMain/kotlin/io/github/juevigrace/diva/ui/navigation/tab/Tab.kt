package io.github.juevigrace.diva.ui.navigation.tab

import androidx.navigation3.runtime.NavKey
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

interface Tab {
    val route: NavKey
    val icon: DrawableResource
    val title: StringResource
}