package io.github.juevigrace.diva.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.ui.NavDisplay
import kotlinx.coroutines.flow.StateFlow

internal interface Navigator<T : NavKey> {
    val startDestination: T
    val backStack: StateFlow<List<T>>
    val currentDestination: StateFlow<T>

    fun navigate(destination: T)
    fun pop()
    fun popUntil(destination: T)

    companion object {
        operator fun<T : NavKey> invoke(startDestination: T): Navigator<T> {
            return NavigatorImpl(startDestination)
        }
    }
}

@Composable
internal fun<T : NavKey> Navigator(
    startScreen: T,
    entryProvider: (T) -> NavEntry<T>,
    modifier: Modifier = Modifier,
    onBack: (() -> Unit)? = null
) {
    val navigator: Navigator<T> = Navigator(startDestination = startScreen)
    Navigator(
        modifier = modifier,
        navigator = navigator,
        onBack = { onBack?.invoke() ?: navigator.pop() },
        entryProvider = entryProvider
    )
}

@Composable
internal fun<T : NavKey> Navigator(
    navigator: Navigator<T>,
    entryProvider: (T) -> NavEntry<T>,
    modifier: Modifier = Modifier,
    onBack: () -> Unit = { navigator.pop() }
) {
    val backStack: List<T> by navigator.backStack.collectAsStateWithLifecycle()
    Navigator(
        modifier = modifier,
        backStack = backStack,
        onBack = onBack,
        entryProvider = entryProvider
    )
}

@Composable
internal fun <T : NavKey> Navigator(
    backStack: List<T>,
    onBack: () -> Unit,
    entryProvider: (T) -> NavEntry<T>,
    modifier: Modifier = Modifier,
) {
    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        onBack = onBack,
        entryProvider = entryProvider
    )
}
