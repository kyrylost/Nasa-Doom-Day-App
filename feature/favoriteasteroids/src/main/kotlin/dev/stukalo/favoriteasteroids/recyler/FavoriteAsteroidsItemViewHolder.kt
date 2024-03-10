package dev.stukalo.favoriteasteroids.recyler

import android.content.Context
import android.os.SystemClock
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import dev.stukalo.common.model.AsteroidUi
import dev.stukalo.favoriteasteroids.R
import dev.stukalo.favoriteasteroids.databinding.ItemFavoriteAsteroidBinding

class FavoriteAsteroidsItemViewHolder(
    val binding: ItemFavoriteAsteroidBinding,
    private val context: Context,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(asteroid: AsteroidUi) {
        asteroid.apply {
            with(binding) {
                tvFavoriteName.text = name
                tvFavoritePotentiallyHazardous.text =
                    if (isPotentiallyHazardousAsteroid == true) {
                        tvFavoritePotentiallyHazardous.setTextColor(
                            ContextCompat.getColor(
                                context, dev.stukalo.ui.R.color.red_900
                            )
                        )
                        ContextCompat.getString(
                            context, R.string.favorite_asteroid_hazardous,
                        )
                    } else {
                        tvFavoritePotentiallyHazardous.setTextColor(
                            ContextCompat.getColor(
                                context, dev.stukalo.ui.R.color.green_900
                            )
                        )
                        ContextCompat.getString(
                            context, R.string.favorite_asteroid_not_hazardous,
                        )
                    }
                chronometerTimer.base = SystemClock.elapsedRealtime() + 20000
                chronometerTimer.start()
            }
        }
    }
}
