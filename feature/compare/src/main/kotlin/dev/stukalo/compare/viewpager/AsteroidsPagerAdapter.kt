package dev.stukalo.compare.viewpager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.stukalo.common.model.AsteroidUi
import dev.stukalo.compare.databinding.ItemViewPagerBinding
import dev.stukalo.datastore.PreferencesManager

class AsteroidsPagerAdapter(
    favoriteAsteroidsOriginal: List<AsteroidUi>,
    private val datastore: PreferencesManager,
) : RecyclerView.Adapter<ViewHolderForPager>() {
    var onItemClick: ((AsteroidUi) -> Unit)? = null

    private val favoriteAsteroids: List<AsteroidUi> =
        listOf(favoriteAsteroidsOriginal.last()) +
            favoriteAsteroidsOriginal + listOf(favoriteAsteroidsOriginal.first())

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolderForPager {
        return ViewHolderForPager(
            ItemViewPagerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
            datastore,
            parent.context,
        )
    }

    override fun getItemCount(): Int = favoriteAsteroids.count()

    override fun onBindViewHolder(
        holder: ViewHolderForPager,
        position: Int,
    ) {
        val prev: AsteroidUi
        val next: AsteroidUi
        when (position) {
            0 -> {
                prev = favoriteAsteroids[favoriteAsteroids.count() - 3]
                next = favoriteAsteroids[1]
            }
            favoriteAsteroids.count() - 1 -> {
                prev = favoriteAsteroids[favoriteAsteroids.count() - 2]
                next = favoriteAsteroids[2]
            }
            else -> {
                prev = favoriteAsteroids[position - 1]
                next = favoriteAsteroids[position + 1]
            }
        }

        holder.apply {
            bind(
                prev,
                favoriteAsteroids[position],
                next,
            )
            binding.root.setOnClickListener {
                onItemClick?.invoke(favoriteAsteroids[position])
            }
        }
    }
}
