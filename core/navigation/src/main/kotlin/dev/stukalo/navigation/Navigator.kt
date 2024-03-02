package dev.stukalo.navigation

import android.net.Uri
import androidx.navigation.NavController

class Navigator {

    lateinit var navController: NavController

    fun navigateTo(
        navigationDirection: NavigationDirection?,
        clearBackStackEntry: Boolean = false,
        deeplink: String? = null,
    ) {
        with(navController) {
            if (clearBackStackEntry) {
                popBackStack()
            }
            if (deeplink.isNullOrEmpty()) {
                when (navigationDirection) {
                    is NavigationDirection.Main -> navigateToMain()
                    else -> {
                        // stub
                    }
                }
            } else {
                navigate(Uri.parse(deeplink))
            }
        }
    }

    private fun navigateToMain() {
        navController.navigate(NavGraphDirections.actionGlobalMain())
    }

}