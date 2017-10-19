package com.arthurnagy.droidconberlin

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.arthurnagy.droidconberlin.injection.app.AppContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPreferencesManager @Inject
constructor(@AppContext context: Context) {

    private val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun getLastSelectedTab() = getInt(LAST_SELECTED_TAB, 0)

    fun setLastSelectedTab(lastSelectedTab: Int) {
        put(LAST_SELECTED_TAB, lastSelectedTab)
    }

    private fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return preferences.getBoolean(key, defaultValue)
    }

    private fun getInt(key: String, defaultValue: Int = DEFAULT_INT_VALUE): Int {
        return preferences.getInt(key, defaultValue)
    }

    private fun getString(key: String): String? {
        return preferences.getString(key, null)
    }

    private fun getStringSet(key: String): Set<String> {
        return preferences.getStringSet(key, setOf())
    }

    private fun put(key: String, value: Boolean) {
        preferences.edit().putBoolean(key, value).apply()
    }

    private fun put(key: String, value: Int) {
        preferences.edit().putInt(key, value).apply()
    }

    private fun put(key: String, value: String) {
        preferences.edit().putString(key, value).apply()
    }

    private fun put(key: String, value: Set<String>) {
        preferences.edit().putStringSet(key, value).apply()
    }

    private fun has(key: String): Boolean {
        return preferences.contains(key)
    }

    private fun remove(key: String) {
        if (has(key)) {
            preferences.edit().remove(key).apply()
        }
    }

    fun clear() {
        preferences.edit().clear().apply()
    }

    companion object {

        private const val LAST_SELECTED_TAB = "lastSelectedTab"
        private const val DEFAULT_INT_VALUE = 0

    }
}