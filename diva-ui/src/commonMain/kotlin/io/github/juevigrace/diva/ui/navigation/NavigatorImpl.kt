package io.github.juevigrace.diva.ui.navigation

import io.github.juevigrace.diva.ui.navigation.routes.Destination

internal class NavigatorImpl<T : Destination>(
    override val startDestination: T
) : NavigatorBase<T>()
