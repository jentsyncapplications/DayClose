package com.jmapps.dayclose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmapps.dayclose.data.datastore.BusinessProfilePreference
import kotlinx.coroutines.launch


class BusinessProfilePreferenceViewModel(
    private val preference: BusinessProfilePreference
) : ViewModel() {

    val businessName = preference.businessName

    val isProfileSet = preference.isProfileSet

    fun saveBusinessName(name: String) {
        viewModelScope.launch {
            preference.saveBusinessName(name)
        }
    }

    fun updateBusinessName(name: String) {
        viewModelScope.launch {
            preference.updateBusinessName(name)
        }
    }
}
