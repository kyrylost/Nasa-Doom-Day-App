package dev.stukalo.favoriteasteroids.recyler

import android.annotation.SuppressLint
import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import dev.stukalo.common.model.AsteroidUi
import dev.stukalo.favoriteasteroids.databinding.ItemFavoriteAsteroidBinding
import dev.stukalo.favoriteasteroids.util.OnSwipeListener

@SuppressLint("ClickableViewAccessibility")
class FavoriteAsteroidsItemAdapter(
    private val context: Context
) : ListAdapter<AsteroidUi, FavoriteAsteroidsItemViewHolder>(DIFF_CALLBACK) {
    val displayMetrics: DisplayMetrics = context.resources.displayMetrics
    val screenWidth = displayMetrics.widthPixels.toFloat()
    var onItemClick: ((AsteroidUi) -> Unit)? = null
    var onDeleteItemClick: ((AsteroidUi) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): FavoriteAsteroidsItemViewHolder {
        return FavoriteAsteroidsItemViewHolder(
            ItemFavoriteAsteroidBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
            context,
        )
    }

    override fun onBindViewHolder(
        holder: FavoriteAsteroidsItemViewHolder,
        position: Int,
    ) {
        with(holder) {
            getItem(position)?.let { asteroid ->
                bind(asteroid)
                with(binding) {
                    cvAsteroid.setOnClickListener {
                        onItemClick?.invoke(asteroid)
                    }

                    cvAsteroid.setOnTouchListener(
                        object : OnSwipeListener(context) {
                            override fun onSwipeLeft() {
                                val deleteWidth = cvDelete.width
                                val cardWidth = cvAsteroid.width

                                cvAsteroid.animate().apply {
                                    translationX(-deleteWidth.toFloat())
                                    duration = 100
                                    start()
                                }

                                cvDelete.animate().apply {
                                    x(
                                        (
                                            cardWidth
                                                .minus(deleteWidth)
                                                .plus(
                                                    TypedValue.applyDimension(
                                                        TypedValue.COMPLEX_UNIT_DIP,
                                                        16F,
                                                        displayMetrics,
                                                    ),
                                                )
                                            ),
                                    )
                                    duration = 100
                                    start()
                                }

                                cvDelete.setOnClickListener {
                                    onDeleteItemClick?.invoke(getItem(holder.adapterPosition))
                                }
                            }

                            override fun onSwipeRight() {
                                cvAsteroid.animate().apply {
                                    translationX(0F)
                                    duration = 100
                                    start()
                                }

                                cvDelete.animate().apply {
                                    x(screenWidth)
                                    duration = 100
                                    start()
                                }
                            }

                            override fun onClick() {
                                with(cvAsteroid) {
                                    callOnClick()
                                    isPressed = true
                                }
                            }
                        },
                    )
                }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<AsteroidUi>() {
                override fun areItemsTheSame(
                    oldItem: AsteroidUi,
                    newItem: AsteroidUi,
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: AsteroidUi,
                    newItem: AsteroidUi,
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}
