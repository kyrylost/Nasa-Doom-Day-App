package dev.stukalo.settings

import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import by.kirich1409.viewbindingdelegate.viewBinding
import dev.stukalo.platform.BaseFragment
import dev.stukalo.settings.databinding.FragmentSettingsBinding
import kotlinx.coroutines.launch

class FragmentSettings : BaseFragment(R.layout.fragment_settings) {
    private val viewBinding: FragmentSettingsBinding by viewBinding(FragmentSettingsBinding::bind)
    private val viewModel: SettingsViewModel by activityViewModels()

    private lateinit var velocityUnitsArrayAdapter: ArrayAdapter<String>
    private lateinit var distanceUnitsArrayAdapter: ArrayAdapter<String>
    private lateinit var diameterUnitsArrayAdapter: ArrayAdapter<String>
    private lateinit var synchronizationTimeArrayAdapter: ArrayAdapter<String>
    private lateinit var minimumIntervalArrayAdapter: ArrayAdapter<String>

    override fun configureUi() {
        super.configureUi()
        velocityUnitsArrayAdapter =
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                resources.getStringArray(R.array.velocity),
            )
        distanceUnitsArrayAdapter =
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                resources.getStringArray(R.array.distance),
            )
        diameterUnitsArrayAdapter =
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                resources.getStringArray(R.array.diameter),
            )
        synchronizationTimeArrayAdapter =
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                resources.getStringArray(R.array.synchronization),
            )
        minimumIntervalArrayAdapter =
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                resources.getStringArray(R.array.interval),
            )

        with(viewBinding) {
            spinnerRelativeVelocityUnits.adapter = velocityUnitsArrayAdapter
            spinnerMissDistanceUnits.adapter = distanceUnitsArrayAdapter
            spinnerEstimatedDiameterUnits.adapter = diameterUnitsArrayAdapter
            spinnerSynchronizationTime.adapter = synchronizationTimeArrayAdapter
            spinnerMinimumInterval.adapter = minimumIntervalArrayAdapter
            btnApply.setOnClickListener {
                viewModel.setNewValues(
                    spinnerRelativeVelocityUnits.selectedItem.toString(),
                    spinnerMissDistanceUnits.selectedItem.toString(),
                    spinnerEstimatedDiameterUnits.selectedItem.toString(),
                    spinnerSynchronizationTime.selectedItem.toString(),
                    spinnerMinimumInterval.selectedItem.toString(),
                )
                operationSucceedSnackBar(getString(R.string.preferences_saved))
            }
        }
        setupSpinnersPosition()
    }

    private fun setupSpinnersPosition() {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                with(viewModel) {
                    getSelectedValues()
                    settingsStateFlow.collect { uiState ->
                        with(viewBinding) {
                            velocityUnitsArrayAdapter.getPosition(
                                uiState.velocityUnit,
                            ).also {
                                spinnerRelativeVelocityUnits.setSelection(it)
                            }

                            distanceUnitsArrayAdapter.getPosition(
                                uiState.distanceUnit,
                            ).also {
                                spinnerMissDistanceUnits.setSelection(it)
                            }

                            diameterUnitsArrayAdapter.getPosition(
                                uiState.diameterUnit,
                            ).also {
                                spinnerEstimatedDiameterUnits.setSelection(it)
                            }

                            synchronizationTimeArrayAdapter.getPosition(
                                uiState.synchronizationTime,
                            ).also {
                                spinnerSynchronizationTime.setSelection(it)
                            }

                            minimumIntervalArrayAdapter.getPosition(
                                uiState.minimumInterval,
                            ).also {
                                spinnerMinimumInterval.setSelection(it)
                            }
                        }
                    }
                }
            }
        }
    }
}
