package dev.stukalo.navigation

interface Navigable {
    fun navigateTo(
        flow: NavigationDirection? = null,
        clearBackStackEntry: Boolean = false,
        deeplink: String? = null,
    )
}
