package dev.stukalo.onboard

import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import dev.stukalo.navigation.NavigationDirection
import dev.stukalo.onboard.databinding.FragmentOnboardingBinding
import dev.stukalo.onboard.viewpager.OnboardingPagerAdapter
import dev.stukalo.platform.BaseFragment

class FragmentOnboarding : BaseFragment(R.layout.fragment_onboarding) {

    private val viewBinding: FragmentOnboardingBinding by viewBinding(FragmentOnboardingBinding::bind)
    private val viewModel: OnboardingViewModel by activityViewModels()

    override fun configureUi() {
        super.configureUi()

        val asteroidsPagerAdapter = OnboardingPagerAdapter()

        with(viewBinding) {
            vpOnboarding.apply{
                adapter = asteroidsPagerAdapter
                setPageTransformer { page, position ->
                    setParallaxTransformation(page, position)
                }
            }

            pbStatus.setProgress(1, true)

            tvNext.setOnClickListener {
                vpOnboarding.currentItem += 1
            }

            tvStart.setOnClickListener {
                if (vpOnboarding.currentItem == 2) {
                    viewModel.setOnboardingShowed()
                    navigateTo(NavigationDirection.Main)
                }
            }

            tvSkip.setOnClickListener {
                viewModel.setOnboardingShowed()
                navigateTo(NavigationDirection.Main)
            }

            setOnPageChangeCallback()

        }
    }

    private fun setOnPageChangeCallback() {
        with(viewBinding) {
            vpOnboarding.apply {
                registerOnPageChangeCallback(
                    object : ViewPager2.OnPageChangeCallback() {
                        override fun onPageScrollStateChanged(state: Int) {
                            super.onPageScrollStateChanged(state)
                            if (state == ViewPager2.SCROLL_STATE_IDLE) {
                                when (currentItem) {
                                    0 -> pbStatus.setProgress(1, true)
                                    1 -> {
                                        pbStatus.setProgress(2, true)
                                        tvStart.isVisible = false
                                    }
                                    2 -> {
                                        pbStatus.setProgress(3, true)
                                        tvStart.isVisible = true
                                    }
                                }
                            }
                        }
                    },
                )
            }
        }
    }

    private fun setParallaxTransformation(page: View, position: Float) {
        page.apply {
            val parallaxView = this.findViewById<View>(R.id.onboardingImage)
            when {
                position < -1 -> alpha = 1f
                position <= 1 -> parallaxView.translationX = position * (width * 2)
                else -> alpha = 1f
            }
        }
    }
}
