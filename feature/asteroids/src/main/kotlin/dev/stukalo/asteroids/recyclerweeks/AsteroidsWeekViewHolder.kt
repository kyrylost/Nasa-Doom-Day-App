package dev.stukalo.asteroids.recyclerweeks

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import dev.stukalo.asteroids.databinding.ItemAsteroidsWeekBinding
import dev.stukalo.asteroids.recyclerasteroids.AsteroidsItemAdapter
import dev.stukalo.repository.model.AsteroidRepo

class AsteroidsWeekViewHolder(
    private val binding: ItemAsteroidsWeekBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bind(dateToAsteroidList: Pair<String, List<AsteroidRepo>>) {
        dateToAsteroidList.apply {
            with(binding) {
                if (second.isNotEmpty()) {
                    tvDate.text = first
                    tvDate.isVisible = true
                    val asteroidsItemAdapter = AsteroidsItemAdapter()
                    asteroidsItemAdapter.submitList(second)
                    rvAsteroidsWeek.adapter = asteroidsItemAdapter
                } else {
                    tvDate.text = null
                    tvDate.isVisible = false
                    rvAsteroidsWeek.adapter = null
                }
            }
        }
    }
}