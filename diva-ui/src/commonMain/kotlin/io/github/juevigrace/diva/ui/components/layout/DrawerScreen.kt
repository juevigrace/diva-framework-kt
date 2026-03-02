package io.github.juevigrace.diva.ui.components.layout

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import io.github.juevigrace.diva.core.Option
import io.github.juevigrace.diva.ui.components.toaster.Toaster

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerScreen(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit,
    drawerContent: @Composable ColumnScope.() -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    snackBarHost: @Composable () -> Unit = { Toaster() },
    containerColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = MaterialTheme.colorScheme.onBackground,
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    content: @Composable (PaddingValues) -> Unit
) {
    val drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed)
    val drawer: @Composable (content: @Composable () -> Unit) -> Unit = remember {
        {
                content ->
            ModalNavigationDrawer(
                drawerContent = {
                    ModalDrawerSheet(
                        drawerState = drawerState,
                        content = drawerContent
                    )
                },
                drawerState = drawerState,
                content = content
            )
        }
    }
    Screen(
        modifier = modifier,
        topBar = topBar,
        drawerWrapper = Option.Some(drawer),
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = floatingActionButtonPosition,
        snackBarHost = snackBarHost,
        containerColor = containerColor,
        contentColor = contentColor,
        contentWindowInsets = contentWindowInsets,
        content = content
    )
}
