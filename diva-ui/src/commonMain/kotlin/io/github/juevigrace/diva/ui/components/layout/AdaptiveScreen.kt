package io.github.juevigrace.diva.ui.components.layout

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import io.github.juevigrace.diva.ui.components.toaster.Toaster
import io.github.juevigrace.diva.ui.window.LocalWindowUtils
import io.github.juevigrace.diva.ui.window.ScreenOrientation
import io.github.juevigrace.diva.ui.window.WindowUtils

@Composable
fun AdaptiveScreen(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    drawerContent: @Composable ColumnScope.() -> Unit = {},
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
    val windowUtils: WindowUtils = LocalWindowUtils.current
    when (windowUtils.orientation) {
        ScreenOrientation.Portrait -> {
            BottomScreen(
                modifier = modifier,
                topBar = topBar,
                bottomBarActions = bottomBarActions,
                bottomFab = bottomFab,
                floatingActionButton = floatingActionButton,
                floatingActionButtonPosition = floatingActionButtonPosition,
                snackBarHost = snackBarHost,
                containerColor = containerColor,
                contentColor = contentColor,
                contentWindowInsets = contentWindowInsets,
                content = content
            )
        }
        ScreenOrientation.Landscape -> {
            DrawerScreen(
                modifier = modifier,
                topBar = topBar,
                drawerContent = drawerContent,
                floatingActionButton = floatingActionButton,
                floatingActionButtonPosition = floatingActionButtonPosition,
                snackBarHost = snackBarHost,
                containerColor = containerColor,
                contentColor = contentColor,
                contentWindowInsets = contentWindowInsets,
                content = content
            )
        }
    }
}
