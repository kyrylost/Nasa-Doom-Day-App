package dev.stukalo.navigation

import android.net.Uri

interface Navigable {
    fun navigateTo(
        flow: NavigationDirection? = null,
        clearBackStackEntry: Boolean = false,
        deeplink: Uri? = null,
        arg: String = "",
    )
}
