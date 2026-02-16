package io.github.juevigrace.diva.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.collections.removeLast

interface Navigator<T : NavKey> {
    val startDestination: T
    val backStack: StateFlow<List<T>>
    val currentDestination: StateFlow<T>

    fun navigate(destination: T)
    fun pop()
    fun popUntil(destination: T)
    fun replaceTop(destination: T)
    fun replaceAll(destination: T)

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

    // TODO: FIX
    override fun navigate(destination: T) {
        mutBackStack.update { state ->
            if (state.contains(destination)) {
                return@update state
            }
            val mut: MutableList<T> = state.toMutableList()
            mut.add(destination)
            updateBackStackFromList(mut.toList())
            updateCurrentDestination()
            state
        }
    }

    override fun pop() {
        mutBackStack.update { state ->
            if (state.isEmpty()) {
                return@update state
            }
            val mut: MutableList<T> = state.toMutableList()
            mut.removeLast()
            updateBackStackFromList(mut.toList())
            updateCurrentDestination()
            state
        }
    }

    override fun popUntil(destination: T) {
        mutBackStack.update { state ->
            if (state.isEmpty() || !state.contains(destination)) {
                return@update state
            }
            val mut: MutableList<T> = state.toMutableList()
            mut.dropLastWhile { it != destination }
            updateBackStackFromList(mut.toList())
            updateCurrentDestination()
            state
        }
    }

    override fun replaceTop(destination: T) {
        mutBackStack.update { state ->
            if (state.isEmpty() || state.last() == destination) {
                return@update state
            }
            val mut: MutableList<T> = state.toMutableList()
            mut.removeLast()
            mut.add(destination)
            updateBackStackFromList(mut.toList())
            updateCurrentDestination()
            state
        }
    }

    override fun replaceAll(destination: T) {
        updateBackStackFromList(listOf(destination))
        updateCurrentDestination()
    }

    protected fun updateBackStackFromList(list: List<T>) {
        mutBackStack.update { list }
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
