package dev.stukalo.onboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.stukalo.datastore.PreferencesManager
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel
    @Inject
    constructor(
        private val datastore: PreferencesManager,
    ) : ViewModel() {
        fun setOnboardingShowed() =
            viewModelScope.launch {
                datastore.setOnboardingShowed(true)
            }
    }
