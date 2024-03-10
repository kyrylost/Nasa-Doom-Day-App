package dev.stukalo.platform

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dev.stukalo.navigation.Navigable
import dev.stukalo.navigation.NavigationDirection
import dev.stukalo.navigation.Navigator
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

abstract class BaseActivity(
    @LayoutRes layout: Int,
) : AppCompatActivity(layout), Navigable {
    val navigator: Navigator = Navigator()

    override fun onCreate(savedInstanceState: Bundle?) {
        runBlocking {
            installSplashScreen()
            delay(3000)
            this@BaseActivity.setTheme(dev.stukalo.ui.R.style.Theme_StukaloKyrylo)
        }
        super.onCreate(savedInstanceState)
        configureUi()
    }

    protected open fun configureUi() = Unit

    override fun navigateTo(
        flow: NavigationDirection?,
        clearBackStackEntry: Boolean,
        deeplink: String?,
        arg: String,
    ) {
        try {
            navigator.navigateTo(flow, clearBackStackEntry, deeplink, arg)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    abstract fun showLoader(isVisible: Boolean)

    abstract fun operationSucceedSnackBar(
        message: String,
        view: View,
    )

    abstract fun operationFailedSnackBar(
        message: String,
        view: View,
    )
}
