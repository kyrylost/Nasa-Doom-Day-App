package dev.stukalo.asteroids.recyclerweeks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import dev.stukalo.asteroids.databinding.ItemAsteroidsWeekBinding
import dev.stukalo.repository.model.AsteroidRepo

class AsteroidsWeekAdapter: PagingDataAdapter<Pair<String, List<AsteroidRepo>>, AsteroidsWeekViewHolder>(
    DIFF_CALLBACK
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): AsteroidsWeekViewHolder {
        return AsteroidsWeekViewHolder(
            ItemAsteroidsWeekBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        )
    }

    override fun onBindViewHolder(
        holder: AsteroidsWeekViewHolder,
        position: Int,
    ) {
        with(holder) {
            getItem(position)?.let { dateToAsteroidList ->
                bind(dateToAsteroidList)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<Pair<String, List<AsteroidRepo>>>() {
                override fun areItemsTheSame(
                    oldItem: Pair<String, List<AsteroidRepo>>,
                    newItem: Pair<String, List<AsteroidRepo>>,
                ): Boolean {
                    return oldItem.first == newItem.first
                }

                override fun areContentsTheSame(
                    oldItem: Pair<String, List<AsteroidRepo>>,
                    newItem: Pair<String, List<AsteroidRepo>>,
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}