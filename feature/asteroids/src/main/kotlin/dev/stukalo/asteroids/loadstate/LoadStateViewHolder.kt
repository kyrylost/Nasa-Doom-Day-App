package dev.stukalo.asteroids.loadstate

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import dev.stukalo.common.R
import dev.stukalo.asteroids.databinding.ItemLoadStateBinding
import dev.stukalo.common.exception.ApiException

class LoadStateViewHolder(
    private val binding: ItemLoadStateBinding,
    private val context: Context,
    private val retry: () -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(loadState: LoadState) {
        binding.apply {
            llError.isVisible = loadState is LoadState.Error
            llLoader.isVisible = loadState is LoadState.Loading

            if (loadState is LoadState.Error) {
                tvErrorMessage.text =
                    ContextCompat.getString(
                        context,
                        (loadState.error as? ApiException)?.errorMessage
                            ?: R.string.unknown_error,
                    )
                llError.setOnClickListener {
                    llError.visibility = View.INVISIBLE
                    retry()
                }
            }
        }
    }
}
