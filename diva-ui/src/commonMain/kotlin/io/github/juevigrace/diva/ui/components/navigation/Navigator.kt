package io.github.juevigrace.diva.ui.components.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import io.github.juevigrace.diva.ui.navigation.Navigator

@Composable
fun <T : NavKey> Navigator(
    startDestination: T,
    modifier: Modifier = Modifier,
    entryDecorators: List<NavEntryDecorator<T>> = listOf(rememberSaveableStateHolderNavEntryDecorator()),
    entryProvider: (key: T) -> NavEntry<T>,
) {
    val navigator: Navigator<T> = Navigator.invoke(startDestination)

    Navigator(
        navigator = navigator,
        modifier = modifier,
        onBack = navigator::pop,
        entryDecorators = entryDecorators,
        entryProvider = entryProvider
    )
}

@Composable
fun<T : NavKey> Navigator(
    navigator: Navigator<T>,
    modifier: Modifier = Modifier,
    onBack: () -> Unit = navigator::pop,
    entryDecorators: List<NavEntryDecorator<T>> = listOf(rememberSaveableStateHolderNavEntryDecorator()),
    entryProvider: (key: T) -> NavEntry<T>
) {
    val backStack: List<T> by navigator.backStack.collectAsStateWithLifecycle()

    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        onBack = onBack,
        entryDecorators = entryDecorators,
        entryProvider = entryProvider
    )
}
