package dev.stukalo.init

import dev.stukalo.navigation.NavigationDirection
import dev.stukalo.platform.BaseFragment

class FragmentInit : BaseFragment(R.layout.fragment_init) {
    override fun configureUi() {
        if (requireActivity().intent.data != null) {
            navigateTo(deeplink = requireActivity().intent.data)
        } else {
            navigateTo(NavigationDirection.Main)
        }
    }
}
