package com.lubna.plantier.ui.welcome

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.lubna.plantier.data.model.UserPreference

class WelcomeViewModel(private val pref: UserPreference) : ViewModel() {

    fun getTheme(): LiveData<Boolean> {
        return pref.getTheme().asLiveData()
    }

}