package dev.stukalo.navigation

import android.net.Uri
import androidx.navigation.NavController

class Navigator {
    lateinit var navController: NavController

    fun navigateTo(
        navigationDirection: NavigationDirection?,
        clearBackStackEntry: Boolean = false,
        deeplink: Uri? = null,
        arg: String = "",
    ) {
        with(navController) {
            if (clearBackStackEntry) {
                popBackStack()
            }
            if (deeplink == null || deeplink.toString().isEmpty()) {
                when (navigationDirection) {
                    is NavigationDirection.Main -> navigateToMain()
                    is NavigationDirection.AsteroidDetails -> navigateToAsteroidDetails(arg)
                    else -> {
                        // stub
                    }
                }
            } else {
                if (deeplink.host == "details") {
                    val asteroidJson = deeplink.toString().split("asteroid_ui_json=")[1]
                    navigateToAsteroidDetails(asteroidJson, true)
                }
            }
        }
    }

    private fun navigateToMain() {
        navController.navigate(NavGraphDirections.actionGlobalMain())
    }

    private fun navigateToAsteroidDetails(
        asteroidUiJson: String,
        navigateFromPush: Boolean = false,
    ) {
        navController.navigate(
            NavGraphDirections.actionGlobalAsteroidDetails(asteroidUiJson, navigateFromPush),
        )
    }
}
