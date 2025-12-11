package io.github.juevigrace.diva.ui.navigation.tabs

import androidx.navigation3.runtime.NavKey
import io.github.juevigrace.diva.ui.navigation.Navigator
import kotlinx.coroutines.flow.StateFlow

internal interface TabNavigator<T : NavKey> : Navigator<T> {
    val tabsStack: StateFlow<LinkedHashMap<T, List<T>>>
    val currentTab: StateFlow<T>

    fun selectTab(tab: T)
    fun popTab()

    companion object {
        operator fun<T : NavKey> invoke(
            startDestination: T,
        ): TabNavigatorImpl<T> {
            return TabNavigatorImpl(startDestination)
        }
    }
}
