package io.github.juevigrace.diva.ui.components.layout

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import io.github.juevigrace.diva.ui.components.layout.bars.bottom.BottomNavBar
import io.github.juevigrace.diva.ui.components.toaster.Toaster

@Composable
fun BottomScreen(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    bottomBarActions: @Composable RowScope.() -> Unit = {},
    bottomFab: Boolean = false,
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    snackBarHost: @Composable () -> Unit = { Toaster() },
    containerColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = MaterialTheme.colorScheme.onBackground,
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    content: @Composable (PaddingValues) -> Unit
) {
    Screen(
        modifier = modifier,
        topBar = topBar,
        bottomBar = {
            BottomNavBar(
                floatingActionButton = if (bottomFab) floatingActionButton else null,
                actions = bottomBarActions
            )
        },
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = floatingActionButtonPosition,
        snackBarHost = snackBarHost,
        containerColor = containerColor,
        contentColor = contentColor,
        contentWindowInsets = contentWindowInsets,
        content = content
    )
}
