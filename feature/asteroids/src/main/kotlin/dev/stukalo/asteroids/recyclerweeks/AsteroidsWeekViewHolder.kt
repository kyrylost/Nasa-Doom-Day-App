package dev.stukalo.asteroids.recyclerweeks

import androidx.recyclerview.widget.RecyclerView
import dev.stukalo.asteroids.databinding.ItemAsteroidsWeekBinding
import dev.stukalo.asteroids.recyclerasteroids.AsteroidsItemAdapter
import dev.stukalo.repository.model.AsteroidRepo

class AsteroidsWeekViewHolder(
    val binding: ItemAsteroidsWeekBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bind(dateToAsteroidList: Pair<String, List<AsteroidRepo>>) {
        dateToAsteroidList.apply {
            with(binding) {
                tvDate.text = first
                val asteroidsItemAdapter = AsteroidsItemAdapter()
                asteroidsItemAdapter.submitList(second)
                rvAsteroidsWeek.apply {
                    adapter = asteroidsItemAdapter
                }
            }
        }
    }
}