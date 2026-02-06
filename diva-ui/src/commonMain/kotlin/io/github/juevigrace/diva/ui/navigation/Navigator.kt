package io.github.juevigrace.diva.ui.navigation

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation3.runtime.NavKey
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

interface Navigator<T : NavKey> {
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

internal abstract class NavigatorBase<T : NavKey> : Navigator<T> {
    protected val mutBackStack = MutableStateFlow(listOf(startDestination))
    override val backStack: StateFlow<List<T>> = mutBackStack.asStateFlow()

    protected val mutCurrentDestination: MutableStateFlow<T> = MutableStateFlow(startDestination)
    override val currentDestination: StateFlow<T> = mutCurrentDestination.asStateFlow()

    protected fun updateBackStackFromList(list: List<T>) {
        mutBackStack.update { list }
    }

    override fun navigate(destination: T) {
        with(mutBackStack.value) {
            if (!contains(destination)) {
                val mut: MutableList<T> = toMutableList()
                mut.add(destination)
                updateBackStackFromList(mut.toList())
                updateCurrentDestination()
            }
        }
    }

    override fun pop() {
        with(mutBackStack.value) {
            if (isNotEmpty()) {
                val mut: MutableList<T> = toMutableList()
                mut.removeLast()
                updateBackStackFromList(mut.toList())
                updateCurrentDestination()
            }
        }
    }

    override fun popUntil(destination: T) {
        with(mutBackStack.value) {
            if (isNotEmpty() && contains(destination)) {
                val mut: MutableList<T> = toMutableList()
                mut.dropLastWhile { it != destination }
                updateBackStackFromList(mut.toList())
                updateCurrentDestination()
            }
        }
    }

    protected fun updateCurrentDestination() {
        if (mutBackStack.value.isNotEmpty()) {
            mutCurrentDestination.update { mutBackStack.value.last() }
        } else {
            mutCurrentDestination.update { startDestination }
        }
    }
}

internal class NavigatorImpl<T : NavKey>(
    override val startDestination: T
) : NavigatorBase<T>()

val LocalNavigator: ProvidableCompositionLocal<Navigator<*>> = compositionLocalOf { error("Navigator not provided") }
