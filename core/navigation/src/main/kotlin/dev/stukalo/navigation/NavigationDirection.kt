package dev.stukalo.navigation

sealed class NavigationDirection {
    data object Init : NavigationDirection()

    data object Main : NavigationDirection()
}
