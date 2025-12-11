package io.github.juevigrace.diva.ui.navigation

import androidx.navigation3.runtime.NavKey

internal class NavigatorImpl<T : NavKey>(
    override val startDestination: T
) : NavigatorBase<T>()
