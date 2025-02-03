package com.herdal.bitcointicker.core.data.local

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PreferencesManager @Inject constructor(
    @ApplicationContext context: Context
) {
    private val preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun isUserLoggedIn(): Boolean {
        return preferences.getBoolean(PREFS_KEY_IS_USER_LOGGED_IN, false)
    }

    fun setUserLoggedIn(isLoggedIn: Boolean) {
        preferences.edit().putBoolean(PREFS_KEY_IS_USER_LOGGED_IN, isLoggedIn).apply()
    }

    companion object {
        private const val PREFERENCES_NAME = "crypto_app_preferences"
        private const val PREFS_KEY_IS_USER_LOGGED_IN = "isUserLoggedIn"
    }
}