package dev.stukalo.init

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dev.stukalo.navigation.NavigationDirection
import dev.stukalo.platform.BaseFragment
import kotlinx.coroutines.launch

class FragmentInit : BaseFragment(R.layout.fragment_init) {

    private val viewModel: InitViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        collectFlow()
        return super.onCreateView(inflater, parent, savedInstanceState)
    }

    private fun collectFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                if (viewModel.isOnboardingShowed()) {
                    if (requireActivity().intent.data != null) {
                        navigateTo(deeplink = requireActivity().intent.data)
                    } else {
                        navigateTo(NavigationDirection.Main)
                    }
                } else {
                    navigateTo(NavigationDirection.Onboarding)
                }
            }
        }
    }
}
