
package com.jmapps.dayclose.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Singleton DataStore instance
val Context.dataStore: DataStore<androidx.datastore.preferences.core.Preferences> by
preferencesDataStore(name = "app_preferences")

class BusinessProfilePreference(private val context: Context) {

    companion object {
        private val BUSINESS_NAME_KEY = stringPreferencesKey("business_name")
        private val IS_PROFILE_SET_KEY = booleanPreferencesKey("is_profile_set")
    }

    // Observe business name
    val businessName: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[BUSINESS_NAME_KEY] ?: ""
        }

    // Observe profile setup status
    val isProfileSet: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[IS_PROFILE_SET_KEY] ?: false
        }

    // Create or update business profile
    suspend fun saveBusinessName(name: String) {
        context.dataStore.edit { preferences ->
            preferences[BUSINESS_NAME_KEY] = name.trim()
            preferences[IS_PROFILE_SET_KEY] = name.isNotBlank()
        }
    }

    // Edit business name
    suspend fun updateBusinessName(name: String) {
        context.dataStore.edit { preferences ->
            preferences[BUSINESS_NAME_KEY] = name.trim()
        }
    }

    // Clear business profile
    suspend fun clearBusinessProfile() {
        context.dataStore.edit { preferences ->
            preferences.remove(BUSINESS_NAME_KEY)
            preferences[IS_PROFILE_SET_KEY] = false
        }
    }
}
