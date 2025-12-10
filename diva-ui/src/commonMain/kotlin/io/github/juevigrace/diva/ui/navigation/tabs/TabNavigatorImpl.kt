package io.github.juevigrace.diva.ui.navigation.tabs

import io.github.juevigrace.diva.ui.navigation.NavigatorBase
import io.github.juevigrace.diva.ui.navigation.routes.Destination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// TODO: this is bad
class TabNavigatorImpl<T : Destination>(
    override val startDestination: T
) : TabNavigator<T> {
    override val tabsStack: StateFlow<LinkedHashMap<T, List<T>>>
        get() = TODO("Not yet implemented")
    override val currentTab: StateFlow<T>
        get() = TODO("Not yet implemented")

    override fun selectTab(tab: T) {
        TODO("Not yet implemented")
    }

    override fun popTab() {
        TODO("Not yet implemented")
    }

    override val backStack: StateFlow<List<T>>
        get() = TODO("Not yet implemented")
    override val currentDestination: StateFlow<T>
        get() = TODO("Not yet implemented")

    override fun navigate(destination: T) {
        TODO("Not yet implemented")
    }

    override fun pop() {
        TODO("Not yet implemented")
    }

    override fun popUntil(destination: T) {
        TODO("Not yet implemented")
    }
}
