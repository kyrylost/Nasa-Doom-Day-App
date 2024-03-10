package dev.stukalo.favoriteasteroids.recyler

import android.content.Context
import android.os.SystemClock
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import dev.stukalo.common.model.AsteroidUi
import dev.stukalo.favoriteasteroids.R
import dev.stukalo.favoriteasteroids.databinding.ItemFavoriteAsteroidBinding
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

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

                val dateTime = LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(closeApproachData?.epochDateCloseApproach ?: 0),
                    ZoneId.systemDefault()
                )

                val futureTimeSeconds = dateTime.atZone(ZoneId.systemDefault()).toEpochSecond()
                val currentTimeSeconds = LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond()

                val countdownTimeSeconds = futureTimeSeconds - currentTimeSeconds

                with(chronometerTimer) {
                    base = SystemClock.elapsedRealtime() + (countdownTimeSeconds * 1000)
                    if (text[0] == '-') {
                        text = ContextCompat.getString(context, R.string.already_passed)
                    }
                    else {
                        start()
                    }
                }
            }
        }
    }
}
