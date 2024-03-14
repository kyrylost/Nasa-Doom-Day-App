package dev.stukalo.onboard.viewpager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.stukalo.onboard.databinding.ViewOnboardingFirstBinding
import dev.stukalo.onboard.databinding.ViewOnboardingSecondBinding
import dev.stukalo.onboard.databinding.ViewOnboardingThirdBinding

class OnboardingPagerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        return when(viewType) {
            0 -> OnboardingFirstScreenViewHolder(
                ViewOnboardingFirstBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                )
            )
            1 -> OnboardingSecondScreenViewHolder(
                ViewOnboardingSecondBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                )
            )
            2 -> OnboardingThirdScreenViewHolder(
                ViewOnboardingThirdBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                )
            )

            else -> {
                OnboardingThirdScreenViewHolder(
                    ViewOnboardingThirdBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false,
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {}

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int = 3
}
