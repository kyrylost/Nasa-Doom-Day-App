package dev.stukalo.init

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.stukalo.datastore.PreferencesManager
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class InitViewModel
    @Inject
    constructor(
        private val datastore: PreferencesManager,
    ) : ViewModel() {
        suspend fun isOnboardingShowed() = datastore.onboardingShowed().first()
    }
