package io.github.juevigrace.diva.ui.navigation.bars

import io.github.juevigrace.diva.core.Option
import io.github.juevigrace.diva.core.getOrElse
import io.github.juevigrace.diva.core.map
import io.github.juevigrace.diva.ui.navigation.tab.Tab

interface NavBarState {
    val tabs: List<Tab>
        get() = emptyList()
    val selectedTabIndex: Int
        get() = 0
    val showBar: Boolean
        get() = true

    val selectedTab: Option<Tab>
        get() = if (tabs.isEmpty()) {
            Option.None
        } else {
            Option.Some(tabs[selectedTabIndex])
        }

    fun updateIndex(index: Int): NavBarState
    fun selectTab(index: Int): NavBarState{
        return when {
            tabs.isEmpty() -> this
            index < 0 || index >= tabs.size -> this
            isSelected(index) -> this
            else -> updateIndex(index)
        }
    }
    fun selectTab(tab: Tab): NavBarState {
        return when {
            tabs.isEmpty() -> this
            !tabs.contains(tab) -> this
            isSelected(tab) -> this
            else -> updateIndex(tabs.indexOf(tab))
        }
    }

    fun toggleBar(): NavBarState
    fun hideBar(): NavBarState{
        if (!isVisible()){
            return this
        }
        return toggleBar()
    }
    fun showBar(): NavBarState{
        if (isVisible()){
            return this
        }
        return toggleBar()
    }

    fun isVisible(): Boolean {
        return showBar
    }
    fun isSelected(tab: Tab): Boolean {
        return selectedTab.map { it == tab }.getOrElse{false}
    }
    fun isSelected(index: Int): Boolean {
        return selectedTabIndex == index
    }
}
