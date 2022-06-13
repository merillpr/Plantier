package com.lubna.plantier.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.lubna.plantier.model.UserModel
import com.lubna.plantier.model.UserPreference
import kotlinx.coroutines.launch

class ProfileViewModel(private val pref: UserPreference) : ViewModel() {

    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }

    fun getTheme(): LiveData<Boolean> {
        return pref.getTheme().asLiveData()
    }

    fun saveTheme(isDarkMode: Boolean) {
        viewModelScope.launch {
            pref.saveTheme(isDarkMode)
        }
    }
}