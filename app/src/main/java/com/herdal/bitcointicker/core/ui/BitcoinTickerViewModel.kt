package com.herdal.bitcointicker.core.ui

import androidx.lifecycle.ViewModel
import com.herdal.bitcointicker.core.data.local.PreferencesManager
import com.herdal.bitcointicker.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BitcoinTickerViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager
) : ViewModel() {
    fun getSetupScreen(): String {
        return if (preferencesManager.isUserLoggedIn()) {
            Screen.Home.route
        } else {
            Screen.Authentication.route
        }
    }
}