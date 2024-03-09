package dev.stukalo.asteroids

import android.view.Gravity
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.transition.Fade
import androidx.transition.Slide
import androidx.transition.Transition
import androidx.transition.TransitionManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import dev.stukalo.asteroids.databinding.FragmentAsteroidsBinding
import dev.stukalo.asteroids.loadstate.LoadStateAdapter
import dev.stukalo.asteroids.recyclerweeks.AsteroidsWeekAdapter
import dev.stukalo.asteroids.util.AsteroidAdapter
import dev.stukalo.asteroids.util.RangeDateValidator
import dev.stukalo.common.Constants.DATE_FORMATTER
import dev.stukalo.common.exception.ApiException
import dev.stukalo.navigation.NavigationDirection
import dev.stukalo.platform.BaseFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

const val DATE_PICKER_TAG = "DATE_PICKER"

class FragmentAsteroids : BaseFragment(R.layout.fragment_asteroids) {
    private val viewBinding: FragmentAsteroidsBinding by viewBinding(FragmentAsteroidsBinding::bind)
    private val viewModel: AsteroidsViewModel by activityViewModels()

    private val formatter = SimpleDateFormat(DATE_FORMATTER, Locale.getDefault())

    private val datePickerBuilder = MaterialDatePicker.Builder.dateRangePicker()

    private val constraintsBuilderRange = CalendarConstraints.Builder()
    private val dateValidator = RangeDateValidator(6)

    private lateinit var datePicker: MaterialDatePicker<androidx.core.util.Pair<Long?, Long?>>

    private var transitionShowFilters: Transition? = null
    private var transitionFilters: Transition? = null

    override fun configureUi() {
        val localDateFormatter = DateTimeFormatter.ofPattern(DATE_FORMATTER)

        val currentDate = LocalDate.now()
        val endDate = currentDate.plusDays(6)

        val from = localDateFormatter.format(currentDate)
        val to = localDateFormatter.format(endDate)

        viewModel.getAsteroids(from, to)

        val asteroidsAdapter = AsteroidsWeekAdapter()
        collectFlows(asteroidsAdapter)
        collectLoadState(asteroidsAdapter)
        setupRecyclerView(asteroidsAdapter)
        initializeTransitions()
        setupDatePicker()

        with(viewBinding) {
            tvFrom.text = from
            tvTo.text = to

            llShowFilters.setOnClickListener {
                TransitionManager.beginDelayedTransition(clAsteroids, transitionFilters)
                llFilters.isVisible = !llFilters.isVisible
            }

            ibCalendar.setOnClickListener {
                datePickerDialog()
                it.background = ContextCompat.getDrawable(requireContext(), R.drawable.bg_calendar_active)
            }

            btnApply.setOnClickListener {
                with(viewModel) {
                    showOnlyHazardous = cbHazardous.isChecked
                    getAsteroids(tvFrom.text.toString(), tvTo.text.toString(), cbDesc.isChecked)
                }
            }
        }
    }

    private fun initializeTransitions() {

        var nextRotationValue = 180F

        with(viewBinding) {
            transitionShowFilters = Fade().apply {
                duration = 200
                addTarget(llShowFilters)
            }

            transitionFilters = Slide(Gravity.TOP).apply {
                duration = 400
                addTarget(llFilters)
                addListener(
                    object : Transition.TransitionListener {
                        override fun onTransitionStart(transition: Transition) {
                            llShowFilters.isVisible = false
                            ivDown.rotation = nextRotationValue
                            nextRotationValue = if (nextRotationValue == 0F) {
                                180F
                            } else {
                                0F
                            }
                        }

                        override fun onTransitionEnd(transition: Transition) {
                            TransitionManager.beginDelayedTransition(clAsteroids, transitionShowFilters)
                            llShowFilters.isVisible = true
                        }

                        override fun onTransitionCancel(transition: Transition) {}

                        override fun onTransitionPause(transition: Transition) {}

                        override fun onTransitionResume(transition: Transition) {}
                    },
                )
            }
        }
    }

    private fun setupRecyclerView(asteroidsWeekAdapter: AsteroidsWeekAdapter) {
        viewBinding.rvAsteroids.apply {
            adapter =
                asteroidsWeekAdapter.withLoadStateFooter(
                    footer = LoadStateAdapter(asteroidsWeekAdapter::retry),
                )
            asteroidsWeekAdapter.onItemClick = {
                navigateTo(NavigationDirection.AsteroidDetails, arg = AsteroidAdapter.toJson(it))
            }
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

    private fun setupDatePicker() {
        constraintsBuilderRange.setValidator(dateValidator)
        datePickerBuilder.setCalendarConstraints(constraintsBuilderRange.build())
        datePicker =
            datePickerBuilder
                .setTheme(dev.stukalo.ui.R.style.ThemeMaterialCalendar)
                .setTitleText(getString(R.string.select_range))
                .build()
        dateValidator.setDatePicker(datePicker)

        datePicker.addOnPositiveButtonClickListener { selection ->
            val startDate = selection.first
            val endDate = selection.second

            val startDateString = startDate?.let { formatter.format(Date(it)) }
            val endDateString = endDate?.let { formatter.format(Date(it)) }

            with(viewBinding) {
                tvFrom.text = startDateString
                tvTo.text = endDateString
            }

            datePicker.dismiss()
        }

        datePicker.addOnDismissListener {
            viewBinding.ibCalendar.background = null
        }
    }

    private fun datePickerDialog() {
        datePicker.show(parentFragmentManager, DATE_PICKER_TAG)
    }
}
