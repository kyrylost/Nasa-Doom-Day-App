package dev.stukalo.main

import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.stukalo.main.databinding.ActivityHostBinding
import dev.stukalo.navigation.NavigationDirection
import dev.stukalo.platform.BaseActivity

@AndroidEntryPoint
class ActivityHost : BaseActivity(R.layout.activity_host) {
    private val viewBinding: ActivityHostBinding by viewBinding(ActivityHostBinding::bind)

    override fun configureUi() {
        (supportFragmentManager.findFragmentById(viewBinding.fcvNavHost.id) as? NavHostFragment)
            ?.let { navHostFragment ->
                navHostFragment.navController.let { navController ->
                    navigator.navController = navController
                }
            }
    }

    override fun navigateTo(
        flow: NavigationDirection?,
        clearBackStackEntry: Boolean,
        deeplink: String?,
    ) {
        navigator.navigateTo(flow, clearBackStackEntry, deeplink)
    }

    override fun showLoader(isVisible: Boolean) {
        viewBinding.pbLoader.isVisible = isVisible
    }
}
