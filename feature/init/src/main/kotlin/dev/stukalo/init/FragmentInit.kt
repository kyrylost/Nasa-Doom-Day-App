package dev.stukalo.init

import dev.stukalo.navigation.NavigationDirection
import dev.stukalo.platform.BaseFragment

class FragmentInit : BaseFragment(R.layout.fragment_init) {
    override fun configureUi() {
        navigateTo(NavigationDirection.Main)
    }
}
