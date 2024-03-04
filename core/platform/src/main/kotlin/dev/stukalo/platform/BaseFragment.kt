package dev.stukalo.platform

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import dev.stukalo.navigation.Navigable
import dev.stukalo.navigation.NavigationDirection

abstract class BaseFragment(
    @LayoutRes val layout: Int,
) : Fragment(), Navigable {
    override fun onCreateView(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(layout, parent, false)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        configureUi()
    }

    protected open fun configureUi() = Unit

    override fun navigateTo(
        flow: NavigationDirection?,
        clearBackStackEntry: Boolean,
        deeplink: String?,
    ) {
        try {
            (requireActivity() as? Navigable)?.navigateTo(
                flow,
                clearBackStackEntry,
                deeplink,
            )
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    private fun requireBaseActivity(): BaseActivity = requireActivity() as BaseActivity

    fun showLoader(isVisible: Boolean) {
        requireBaseActivity().showLoader(isVisible)
    }
}
