package com.lubna.plantier.view.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lubna.plantier.model.UserModel
import com.lubna.plantier.model.UserPreference
import kotlinx.coroutines.launch

class SignupViewModel(private val pref: UserPreference) : ViewModel() {
    fun saveUser(user: UserModel) {
        viewModelScope.launch {
            pref.saveUser(user)
        }
    }
}