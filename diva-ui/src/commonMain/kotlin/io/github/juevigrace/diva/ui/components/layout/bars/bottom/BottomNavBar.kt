package io.github.juevigrace.diva.ui.components.layout.bars.bottom

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BottomAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier,
    floatingActionButton: (@Composable () -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit,
) {
    BottomAppBar(
        modifier = modifier,
        actions = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                actions()
            }
        },
        floatingActionButton = floatingActionButton,
    )
}
