package dev.stukalo.favoriteasteroids

import android.net.Uri
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dev.stukalo.common.utils.AsteroidAdapter
import dev.stukalo.favoriteasteroids.databinding.FragmentFavoriteAsteroidsBinding
import dev.stukalo.favoriteasteroids.recyler.FavoriteAsteroidsItemAdapter
import dev.stukalo.navigation.NavigationDirection
import dev.stukalo.platform.BaseFragment
import kotlinx.coroutines.launch

class FragmentFavoriteAsteroids : BaseFragment(R.layout.fragment_favorite_asteroids) {

    private val viewBinding: FragmentFavoriteAsteroidsBinding by viewBinding(FragmentFavoriteAsteroidsBinding::bind)
    private val viewModel: FavoriteAsteroidsViewModel by activityViewModels()

    override fun configureUi() {
        super.configureUi()
        viewModel.collectAsteroids()
        val portfolioItemAdapter = FavoriteAsteroidsItemAdapter(requireContext())
        setupRecyclerView(portfolioItemAdapter)
        collectFlows(portfolioItemAdapter)
    }

    private fun setupRecyclerView(favoriteAsteroidsItemAdapter: FavoriteAsteroidsItemAdapter) {
        viewBinding.rvFavoriteAsteroids.apply {
            adapter = favoriteAsteroidsItemAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        favoriteAsteroidsItemAdapter.onDeleteItemClick = {
            it.id?.let { id ->
                viewModel.deleteAsteroid(id)
            }
        }
        favoriteAsteroidsItemAdapter.onItemClick = {
            navigateTo(NavigationDirection.AsteroidDetails, arg = AsteroidAdapter.toJson(it))
        }
    }

    private fun collectFlows(favoriteAsteroidsItemAdapter: FavoriteAsteroidsItemAdapter) {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                with(viewModel) {
                    favoriteAsteroidsStateFlow.collect { uiState ->
                        with(viewBinding) {
                            if (uiState.favoriteAsteroids.isNotEmpty()) {
                                tvEmpty.visibility = View.GONE
                                rvFavoriteAsteroids.visibility = View.VISIBLE
                                favoriteAsteroidsItemAdapter.submitList(uiState.favoriteAsteroids)
                            } else {
                                rvFavoriteAsteroids.visibility = View.GONE
                                tvEmpty.visibility = View.VISIBLE
                            }
                        }
                    }
                }
            }
        }
    }

}
