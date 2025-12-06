package io.github.juevigrace.diva.ui.navigation

interface Navigator<T> {
    suspend fun navigate(destination: T)
    suspend fun pop()
    suspend fun popUntil(destination: T)
}
