package dev.stukalo.asteroids.recyclerweeks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import dev.stukalo.asteroids.databinding.ItemAsteroidsWeekBinding
import dev.stukalo.common.model.AsteroidUi

class AsteroidsWeekAdapter : PagingDataAdapter<Pair<String, List<AsteroidUi>>, AsteroidsWeekViewHolder>(
    DIFF_CALLBACK,
) {
    var onItemClick: ((AsteroidUi) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): AsteroidsWeekViewHolder {
        return AsteroidsWeekViewHolder(
            ItemAsteroidsWeekBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
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
            onItemClick = {
                this@AsteroidsWeekAdapter.onItemClick?.invoke(it)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<Pair<String, List<AsteroidUi>>>() {
                override fun areItemsTheSame(
                    oldItem: Pair<String, List<AsteroidUi>>,
                    newItem: Pair<String, List<AsteroidUi>>,
                ): Boolean {
                    return oldItem.first == newItem.first
                }

                override fun areContentsTheSame(
                    oldItem: Pair<String, List<AsteroidUi>>,
                    newItem: Pair<String, List<AsteroidUi>>,
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}
