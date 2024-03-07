package dev.stukalo.navigation

import android.net.Uri
import android.util.Log
import androidx.navigation.NavController

class Navigator {
    lateinit var navController: NavController

    fun navigateTo(
        navigationDirection: NavigationDirection?,
        clearBackStackEntry: Boolean = false,
        deeplink: String? = null,
        arg: String = "",
    ) {
        with(navController) {
            if (clearBackStackEntry) {
                popBackStack()
            }
            if (deeplink.isNullOrEmpty()) {
                when (navigationDirection) {
                    is NavigationDirection.Main -> navigateToMain()
                    is NavigationDirection.AsteroidDetails -> navigateToAsteroidDetails(arg)
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

    private fun navigateToAsteroidDetails(arg: String) {
        Log.d("TDXCGHBJMKL", arg)
        navController.navigate(NavGraphDirections.actionGlobalAsteroidDetails(arg))
    }
}
