package io.github.juevigrace.diva.ui.navigation.tabs

import io.github.juevigrace.diva.ui.navigation.Navigator
import io.github.juevigrace.diva.ui.navigation.routes.Destination
import kotlinx.coroutines.flow.StateFlow

interface TabNavigator<T : Destination> : Navigator<T> {
    val tabsStack: StateFlow<LinkedHashMap<T, List<T>>>
    val currentTab: StateFlow<T>

    fun selectTab(tab: T)
    fun popTab()

    companion object {
        operator fun<T : Destination> invoke(
            startDestination: T,
        ): TabNavigatorImpl<T> {
            return TabNavigatorImpl(startDestination)
        }
    }
}
