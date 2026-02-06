package io.github.juevigrace.diva.ui.navigation

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation3.runtime.NavKey
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.collections.removeLast

interface Navigator {
    val startDestination:NavKey
    val backStack: StateFlow<List<NavKey>>
    val currentDestination: StateFlow<NavKey>

    fun navigate(destination: NavKey)
    fun pop()
    fun popUntil(destination: NavKey)

    companion object {
        operator fun invoke(startDestination: NavKey    ): Navigator {
            return NavigatorImpl(startDestination)
        }
    }
}

internal abstract class NavigatorBase : Navigator {
    protected val mutBackStack = MutableStateFlow(listOf(startDestination))
    override val backStack: StateFlow<List<NavKey>> = mutBackStack.asStateFlow()

    protected val mutCurrentDestination: MutableStateFlow<NavKey> = MutableStateFlow(startDestination)
    override val currentDestination: StateFlow<NavKey> = mutCurrentDestination.asStateFlow()

    protected fun updateBackStackFromList(list: List<NavKey>) {
        mutBackStack.update { list }
    }

    override fun navigate(destination: NavKey) {
        with(mutBackStack.value) {
            if (!contains(destination)) {
                val mut: MutableList<NavKey> = toMutableList()
                mut.add(destination)
                updateBackStackFromList(mut.toList())
                updateCurrentDestination()
            }
        }
    }

    override fun pop() {
        with(mutBackStack.value) {
            if (isNotEmpty()) {
                val mut: MutableList<NavKey> = toMutableList()
                mut.removeLast()
                updateBackStackFromList(mut.toList())
                updateCurrentDestination()
            }
        }
    }

    override fun popUntil(destination: NavKey) {
        with(mutBackStack.value) {
            if (isNotEmpty() && contains(destination)) {
                val mut: MutableList<NavKey> = toMutableList()
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

internal class NavigatorImpl(
    override val startDestination: NavKey
) : NavigatorBase()

val LocalNavigator: ProvidableCompositionLocal<Navigator> = compositionLocalOf { error("Navigator not provided") }
