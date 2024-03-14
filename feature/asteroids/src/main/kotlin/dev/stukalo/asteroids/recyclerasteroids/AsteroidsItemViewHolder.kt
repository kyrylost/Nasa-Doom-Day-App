package dev.stukalo.asteroids.recyclerasteroids

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import dev.stukalo.asteroids.R
import dev.stukalo.asteroids.databinding.ItemAsteroidBinding
import dev.stukalo.common.model.AsteroidUi

class AsteroidsItemViewHolder(
    val binding: ItemAsteroidBinding,
    private val context: Context,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(asteroid: AsteroidUi) {
        asteroid.apply {
            with(binding) {
                tvName.text = name
                tvHazardousValue.text =
                    if (isPotentiallyHazardousAsteroid == true) {
                        ContextCompat.getString(
                            context, dev.stukalo.ui.R.string.yes,
                        )
                    } else {
                        ContextCompat.getString(
                            context, dev.stukalo.ui.R.string.no,
                        )
                    }
                tvOrbitingBodyValue.text = closeApproachData?.orbitingBody
                tvEstimatedDiameterMinValue.text =
                    String.format(
                        ContextCompat.getString(context, dev.stukalo.ui.R.string.estimated_diameter_value),
                        estimatedDiameter?.kilometers?.estimatedDiameterMin,
                    )
                tvEstimatedDiameterMaxValue.text =
                    String.format(
                        ContextCompat.getString(context, dev.stukalo.ui.R.string.estimated_diameter_value),
                        estimatedDiameter?.kilometers?.estimatedDiameterMax,
                    )
            }
        }
    }
}
