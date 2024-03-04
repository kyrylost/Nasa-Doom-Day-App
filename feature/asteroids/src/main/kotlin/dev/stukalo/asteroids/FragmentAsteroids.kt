package dev.stukalo.asteroids

import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import by.kirich1409.viewbindingdelegate.viewBinding
import dev.stukalo.asteroids.databinding.FragmentAsteroidsBinding
import dev.stukalo.asteroids.loadstate.LoadStateAdapter
import dev.stukalo.asteroids.recyclerweeks.AsteroidsWeekAdapter
import dev.stukalo.common.exception.ApiException
import dev.stukalo.platform.BaseFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FragmentAsteroids : BaseFragment(R.layout.fragment_asteroids) {

    private val viewBinding: FragmentAsteroidsBinding by viewBinding(FragmentAsteroidsBinding::bind)
    private val viewModel: AsteroidsViewModel by activityViewModels()

    override fun configureUi() {
        viewModel.getAsteroids("2024-03-04","2024-03-11")
        val asteroidsAdapter = AsteroidsWeekAdapter()
        collectFlows(asteroidsAdapter)
        collectLoadState(asteroidsAdapter)

        viewBinding.rvAsteroids.apply {
            adapter = asteroidsAdapter
                    .withLoadStateFooter(
                        footer = LoadStateAdapter(asteroidsAdapter::retry),
                    )
        }
    }

    private fun collectFlows(asteroidsWeekAdapter: AsteroidsWeekAdapter) {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                with(viewModel) {
                    asteroidsStateFlow.collectLatest { uiState ->
                        asteroidsWeekAdapter.submitData(uiState.asteroids)
                    }
                }
            }
        }
    }

    private fun collectLoadState(asteroidsWeekAdapter: AsteroidsWeekAdapter) {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                asteroidsWeekAdapter.loadStateFlow.collectLatest {
                    showLoader(it.refresh is LoadState.Loading)

                    if (it.refresh is LoadState.Error) {
                        val error = (it.refresh as LoadState.Error).error
                        with(viewBinding) {
                            tvErrorMessage.text =
                                ContextCompat.getString(
                                    requireContext(),
                                    (error as? ApiException)?.errorMessage
                                        ?: dev.stukalo.common.R.string.unknown_error,
                                )

                            llRetry.apply {
                                visibility = View.VISIBLE
                                setOnClickListener {
                                    asteroidsWeekAdapter.refresh()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
