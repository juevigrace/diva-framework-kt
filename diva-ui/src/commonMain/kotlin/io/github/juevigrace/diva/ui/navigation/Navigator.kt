package io.github.juevigrace.diva.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import io.github.juevigrace.diva.ui.navigation.routes.Destination
import kotlinx.coroutines.flow.StateFlow

interface Navigator<T : Destination> {
    val startDestination: T
    val backStack: StateFlow<List<T>>
    val currentDestination: StateFlow<T>

    fun navigate(destination: T)
    fun pop()
    fun popUntil(destination: T)

    companion object {
        operator fun<T : Destination> invoke(startDestination: T): Navigator<T> {
            return NavigatorImpl(startDestination)
        }
    }
}

@Composable
fun<T : Destination> Navigator(
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
fun<T : Destination> Navigator(
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
fun <T : Destination> Navigator(
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
