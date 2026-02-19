package io.github.juevigrace.diva.ui.navigation

import androidx.navigation3.runtime.NavKey
import io.github.juevigrace.diva.core.Option
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class BackStack<T : NavKey>(
    val current: Option<T>,
    val entries: List<T>,
)

interface Navigator<T : NavKey> {
    val startDestination: T
    val backStack: StateFlow<BackStack<T>>

    fun navigate(destination: T)
    fun pop()
    fun popUntil(destination: T)
    fun replaceTop(destination: T)
    fun replaceAll(destination: T)

    companion object {
        fun<T : NavKey> newInstance(startDestination: T): Navigator<T> {
            return NavigatorImpl(startDestination)
        }
    }
}

abstract class NavigatorBase<T : NavKey>(
    start: T
) : Navigator<T> {
    override val startDestination: T = start
    protected val mutBackStack: MutableStateFlow<BackStack<T>> = MutableStateFlow(
        BackStack(
            current = Option.Some(startDestination),
            entries = listOf(startDestination)
        )
    )
    override val backStack: StateFlow<BackStack<T>> = mutBackStack.asStateFlow()

    override fun navigate(destination: T) {
        mutBackStack.update { state ->
            if (state.entries.contains(destination)) {
                return@update state
            }
            val mut: MutableList<T> = state.entries.toMutableList()
            mut.add(destination)
            state.copy(entries = mut.toList(), current = Option.of(destination))
        }
    }

    override fun pop() {
        mutBackStack.update { state ->
            if (state.entries.isEmpty()) {
                return@update state
            }
            val mut: MutableList<T> = state.entries.toMutableList()
            mut.removeLast()
            state.copy(entries = mut.toList(), current = Option.of(mut.lastOrNull()))
        }
    }

    override fun popUntil(destination: T) {
        mutBackStack.update { state ->
            if (state.entries.isEmpty() || !state.entries.contains(destination)) {
                return@update state
            }
            val mut: MutableList<T> = state.entries.toMutableList()
            val newList = mut.dropLastWhile { it != destination }
            state.copy(entries = newList.toList(), current = Option.of(newList.lastOrNull()))
        }
    }

    override fun replaceTop(destination: T) {
        mutBackStack.update { state ->
            if (state.entries.isEmpty() || state.entries.last() == destination) {
                return@update state
            }
            val mut: MutableList<T> = state.entries.toMutableList()
            mut.removeLast()
            mut.add(destination)
            state.copy(entries = mut.toList(), current = Option.of(destination))
        }
    }

    override fun replaceAll(destination: T) {
        mutBackStack.update { state ->
            state.copy(current = Option.of(destination), entries = listOf(destination))
        }
    }
}

internal class NavigatorImpl<T : NavKey>(
    start: T
) : NavigatorBase<T>(start)
