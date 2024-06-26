package dev.stukalo.navigation

sealed class NavigationDirection {
    data object Init : NavigationDirection()

    data object Main : NavigationDirection()

    data object AsteroidDetails : NavigationDirection()

    data object CompareAsteroids : NavigationDirection()

    data object Onboarding : NavigationDirection()
}
