package dev.stukalo.main

import android.os.Bundle
import android.util.SparseArray
import androidx.core.util.forEach
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import dev.stukalo.main.databinding.FragmentMainBinding
import dev.stukalo.platform.BaseFragment

const val LAST_SELECTED_ITEM_TAG_KEY = "item_tag"

@AndroidEntryPoint
class FragmentMain : BaseFragment(R.layout.fragment_main) {
    private val viewBinding: FragmentMainBinding by viewBinding(FragmentMainBinding::bind)
    private var lastSelectedItemTag: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lastSelectedItemTag = savedInstanceState?.getString(LAST_SELECTED_ITEM_TAG_KEY, null)
    }

    override fun configureUi() {
        viewBinding.bnvNavBar.setupWithNavController(
            navGraphIds =
                listOf(
                    dev.stukalo.navigation.R.navigation.asteroids_nav_graph,
                    dev.stukalo.navigation.R.navigation.favorite_asteroids_nav_graph,
                    dev.stukalo.navigation.R.navigation.settings_nav_graph,
                ),
            fragmentManager = childFragmentManager,
            containerId = R.id.fcv_main_nav_host,
        )
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        lastSelectedItemTag?.let {
            outState.putString(LAST_SELECTED_ITEM_TAG_KEY, it)
        }
    }

    /**
     * Manages the various graphs needed for a [BottomNavigationView].
     * Source: https://gist.github.com/eugene-drapoguz/a176d53e2188c0030b10c71cccfadbc0
     */
    private fun BottomNavigationView?.setupWithNavController(
        navGraphIds: List<Int>,
        fragmentManager: FragmentManager,
        containerId: Int,
    ) {
        // Map of tags
        val graphIdToTagMap = SparseArray<String>()
        // Result. Selected controller
        var selectedNavController: NavController? = null

        var firstFragmentGraphId = 0

        // First create a NavHostFragment for each NavGraph ID
        navGraphIds.forEachIndexed { index, navGraphId ->
            val fragmentTag = getFragmentTag(index)

            // Find or create the Navigation host fragment
            val navHostFragment =
                obtainNavHostFragment(
                    fragmentManager,
                    fragmentTag,
                    navGraphId,
                    containerId,
                )

            // Obtain its id
            val graphId = navHostFragment.navController.graph.id

            if (index == 0) {
                firstFragmentGraphId = graphId
            }

            // Save to the map
            graphIdToTagMap[graphId] = fragmentTag

            // Attach or detach nav host fragment depending on whether it's the selected item.
            if (lastSelectedItemTag == null) {
                if (index == 0) {
                    selectedNavController = navHostFragment.navController
                    attachNavHostFragment(fragmentManager, navHostFragment, true)
                } else {
                    detachNavHostFragment(fragmentManager, navHostFragment)
                }
            } else {
                if (lastSelectedItemTag == fragmentTag) {
                    selectedNavController = navHostFragment.navController
                    attachNavHostFragment(fragmentManager, navHostFragment, index == 0)
                } else {
                    detachNavHostFragment(fragmentManager, navHostFragment)
                }
            }
        }

        // Now connect selecting an item with swapping Fragments
        val firstFragmentTag = graphIdToTagMap[firstFragmentGraphId]
        var selectedItemTag = lastSelectedItemTag ?: firstFragmentTag
        var isOnFirstFragment = selectedItemTag == firstFragmentTag

        // When a navigation item is selected
        this?.setOnItemSelectedListener { item ->
            // Don't do anything if the state is state has already been saved.
            if (fragmentManager.isStateSaved) {
                false
            } else {
                val newlySelectedItemTag = graphIdToTagMap[item.itemId]
                if (selectedItemTag != newlySelectedItemTag) {
                    // Pop everything above the first fragment (the "fixed start destination")
                    fragmentManager.popBackStack(
                        firstFragmentTag,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE,
                    )
                    val selectedFragment =
                        fragmentManager.findFragmentByTag(newlySelectedItemTag)
                            as NavHostFragment

                    // Exclude the first fragment tag because it's always in the back stack.
                    if (firstFragmentTag != newlySelectedItemTag) {
                        // Commit a transaction that cleans the back stack and adds the first fragment
                        // to it, creating the fixed started destination.
                        fragmentManager.beginTransaction()
                            .attach(selectedFragment)
                            .setPrimaryNavigationFragment(selectedFragment)
                            .apply {
                                // Detach all other Fragments
                                graphIdToTagMap.forEach { _, fragmentTagIter ->
                                    if (fragmentTagIter != newlySelectedItemTag) {
                                        detach(fragmentManager.findFragmentByTag(firstFragmentTag)!!)
                                    }
                                }
                            }
                            .addToBackStack(firstFragmentTag)
                            .setReorderingAllowed(true)
                            .commit()
                    }
                    selectedItemTag = newlySelectedItemTag
                    isOnFirstFragment = selectedItemTag == firstFragmentTag
                    selectedNavController = selectedFragment.navController
                    lastSelectedItemTag = selectedItemTag
                    true
                } else {
                    false
                }
            }
        }

        // Optional: on item reselected, pop back stack to the destination of the graph
        this?.setupItemReselected(graphIdToTagMap, fragmentManager)

        // Finally, ensure that we update our BottomNavigationView when the back stack changes
        fragmentManager.addOnBackStackChangedListener {
            if (!isOnFirstFragment && !fragmentManager.isOnBackStack(firstFragmentTag)) {
                this?.selectedItemId = firstFragmentGraphId
            }

            // Reset the graph if the currentDestination is not valid (happens when the back
            // stack is popped after using the back button).
            selectedNavController?.let { controller ->
                if (controller.currentDestination == null) {
                    controller.navigate(controller.graph.id)
                }
            }
        }
    }

    private fun BottomNavigationView.setupItemReselected(
        graphIdToTagMap: SparseArray<String>,
        fragmentManager: FragmentManager,
    ) {
        setOnItemReselectedListener { item ->
            val newlySelectedItemTag = graphIdToTagMap[item.itemId]
            val selectedFragment =
                fragmentManager.findFragmentByTag(newlySelectedItemTag)
                    as NavHostFragment
            val navController = selectedFragment.navController
            // Pop the back stack to the start destination of the current navController graph
            navController.popBackStack(
                navController.graph.startDestinationId,
                false,
            )
        }
    }

    private fun detachNavHostFragment(
        fragmentManager: FragmentManager,
        navHostFragment: NavHostFragment,
    ) {
        fragmentManager.beginTransaction()
            .detach(navHostFragment)
            .commitNow()
    }

    private fun attachNavHostFragment(
        fragmentManager: FragmentManager,
        navHostFragment: NavHostFragment,
        isPrimaryNavFragment: Boolean,
    ) {
        fragmentManager.beginTransaction()
            .attach(navHostFragment)
            .apply {
                if (isPrimaryNavFragment) {
                    setPrimaryNavigationFragment(navHostFragment)
                }
            }
            .commitNow()
    }

    private fun obtainNavHostFragment(
        fragmentManager: FragmentManager,
        fragmentTag: String,
        navGraphId: Int,
        containerId: Int,
    ): NavHostFragment {
        // If the Nav Host fragment exists, return it
        val existingFragment = fragmentManager.findFragmentByTag(fragmentTag) as NavHostFragment?
        existingFragment?.let { return it }

        // Otherwise, create it and return it.
        val navHostFragment = NavHostFragment.create(navGraphId)
        fragmentManager.beginTransaction()
            .add(containerId, navHostFragment, fragmentTag)
            .commitNow()
        return navHostFragment
    }

    private fun FragmentManager.isOnBackStack(backStackName: String): Boolean {
        val backStackCount = backStackEntryCount
        for (index in 0 until backStackCount) {
            if (getBackStackEntryAt(index).name == backStackName) {
                return true
            }
        }
        return false
    }

    private fun getFragmentTag(index: Int) = "bottomNavigation#$index"
}
