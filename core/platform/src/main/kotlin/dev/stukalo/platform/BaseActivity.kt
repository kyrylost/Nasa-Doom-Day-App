package dev.stukalo.platform

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import dev.stukalo.navigation.NavigationDirection
import dev.stukalo.navigation.Navigator
import dev.stukalo.navigation.Navigable

abstract class BaseActivity(
    @LayoutRes layout: Int,
) : AppCompatActivity(layout), Navigable {
    val navigator: Navigator = Navigator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configureUi()
    }

    protected open fun configureUi() = Unit

    override fun navigateTo(
        flow: NavigationDirection?,
        clearBackStackEntry: Boolean,
        deeplink: String?
    ) {
        try {
            navigator.navigateTo(flow, clearBackStackEntry, deeplink)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}