package dev.stukalo.init

import by.kirich1409.viewbindingdelegate.viewBinding
import dev.stukalo.init.databinding.FragmentInitBinding
import dev.stukalo.navigation.NavigationDirection
import dev.stukalo.platform.BaseFragment

class FragmentInit: BaseFragment(R.layout.fragment_init) {
    private val viewBinding: FragmentInitBinding by viewBinding(FragmentInitBinding::bind)

    override fun configureUi() {
        viewBinding.button.setOnClickListener {
            navigateTo(NavigationDirection.Main)
        }
    }
}