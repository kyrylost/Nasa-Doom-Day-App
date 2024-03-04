package dev.stukalo.asteroids.recyclerasteroids

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import dev.stukalo.asteroids.databinding.ItemAsteroidBinding
import dev.stukalo.repository.model.AsteroidRepo

class AsteroidsItemAdapter: ListAdapter<AsteroidRepo, AsteroidsItemViewHolder>(DIFF_CALLBACK) {
    var onItemClick: ((AsteroidRepo) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): AsteroidsItemViewHolder {
        return AsteroidsItemViewHolder(
            ItemAsteroidBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
            parent.context,
        )
    }

    override fun onBindViewHolder(
        holder: AsteroidsItemViewHolder,
        position: Int,
    ) {
        with(holder) {
            getItem(position)?.let { asteroid ->
                bind(asteroid)
                binding.root.setOnClickListener {
                    onItemClick?.invoke(asteroid)
                }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<AsteroidRepo>() {
                override fun areItemsTheSame(
                    oldItem: AsteroidRepo,
                    newItem: AsteroidRepo,
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: AsteroidRepo,
                    newItem: AsteroidRepo,
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}