package dev.stukalo.asteroids.recyclerweeks

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import dev.stukalo.asteroids.databinding.ItemAsteroidsWeekBinding
import dev.stukalo.asteroids.recyclerasteroids.AsteroidsItemAdapter
import dev.stukalo.common.model.AsteroidUi

class AsteroidsWeekViewHolder(
    private val binding: ItemAsteroidsWeekBinding,
) : RecyclerView.ViewHolder(binding.root) {
    var onItemClick: ((AsteroidUi) -> Unit)? = null

    fun bind(dateToAsteroidList: Pair<String, List<AsteroidUi>>) {
        dateToAsteroidList.apply {
            with(binding) {
                if (second.isNotEmpty()) {
                    tvDate.text = first
                    tvDate.isVisible = true
                    val asteroidsItemAdapter = AsteroidsItemAdapter()
                    asteroidsItemAdapter.submitList(second)
                    rvAsteroidsWeek.adapter = asteroidsItemAdapter

                    asteroidsItemAdapter.onItemClick = {
                        onItemClick?.invoke(it)
                    }
                } else {
                    tvDate.text = null
                    tvDate.isVisible = false
                    rvAsteroidsWeek.adapter = null
                }
            }
        }
    }
}
