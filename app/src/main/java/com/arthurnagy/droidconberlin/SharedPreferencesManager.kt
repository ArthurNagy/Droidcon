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

    fun saveSessionIds(sessionIds: List<String>) {
        put(SAVED_SESSION_IDS, sessionIds.toSet())
    }

    fun saveSessionId(sessionId: String) {
        val savedSessions = getStringSet(SAVED_SESSION_IDS).toMutableList()
        savedSessions.add(sessionId)
        put(SAVED_SESSION_IDS, savedSessions.toSet())
    }

    fun getSavedSessionIds(): List<String> {
        return getStringSet(SAVED_SESSION_IDS).toList()
    }

    // Do not expose the methods that read and write primitive values with generalized key values. They should always be
    // private.

    //region READ VALUES
    // Methods use a default parameter or not based on the most frequent use cases. You can modify them to your needs as
    // you wish.

    /**
     * Reads a boolean with the given default value from the given key.
     */
    private fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return preferences.getBoolean(key, defaultValue)
    }

    /**
     * Reads an integer from the given key
     */
    private fun getInt(key: String, defaultValue: Int = DEFAULT_INT_VALUE): Int {
        return preferences.getInt(key, defaultValue)
    }

    /**
     * Reads a string with NULL default value from the given key.
     */
    private fun getString(key: String): String? {
        return preferences.getString(key, null)
    }

    private fun getStringSet(key: String): Set<String> {
        return preferences.getStringSet(key, setOf())
    }
    //endregion

    //region WRITE VALUES
    // Do not optimize the class by moving the Editor instance to a global variable. It's basically a hash map that get's
    // cleared after each apply and commit. Since we should only use apply methods for writing, which is async, it
    // wouldn't make a global Editor safe to use.

    /**
     * Sets the given boolean for the given key.
     */
    private fun put(key: String, value: Boolean) {
        preferences.edit().putBoolean(key, value).apply()
    }

    /**
     * Sets the given integer value for the given key.
     */
    private fun put(key: String, value: Int) {
        preferences.edit().putInt(key, value).apply()
    }

    /**
     * Sets the given string for the given key.
     */
    private fun put(key: String, value: String) {
        preferences.edit().putString(key, value).apply()
    }

    private fun put(key: String, value: Set<String>) {
        preferences.edit().putStringSet(key, value).apply()
    }
    //endregion

    //region UTILITY METHODS
    // Visibility of the utility methods can be changed if there's a strong reason for it.

    /**
     * Checks if there's any preference stored under the given key or not.
     */
    private fun has(key: String): Boolean {
        return preferences.contains(key)
    }

    /**
     * Removes the values under the given key, but only if the given key is still available in the preferences.
     */
    private fun remove(key: String) {
        if (has(key)) {
            preferences.edit().remove(key).apply()
        }
    }

    /**
     * Clears the shared preferences file by removing all entries.
     */
    fun clear() {
        preferences.edit().clear().apply()
    }

    companion object {

        //region PREFERENCE KEYS
        // Place for defining your preference keys. All of them should be private constants.
        private const val SAVED_SESSION_IDS = "savedSessionIds"
        //endregion

        private const val DEFAULT_INT_VALUE = 0
    }
    //endregion
}
/**
 * Reads an integer with [.DEFAULT_INT_VALUE] default value from the given key.
 */