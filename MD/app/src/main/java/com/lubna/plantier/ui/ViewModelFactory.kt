package com.lubna.plantier.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lubna.plantier.model.UserPreference
import com.lubna.plantier.ui.analysis.AnalysisViewModel
import com.lubna.plantier.ui.login.LoginViewModel
import com.lubna.plantier.ui.main.MainViewModel
import com.lubna.plantier.ui.profile.ProfileViewModel
import com.lubna.plantier.ui.signup.SignupViewModel

class ViewModelFactory(private val pref: UserPreference) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(pref) as T
            }
            modelClass.isAssignableFrom(SignupViewModel::class.java) -> {
                SignupViewModel(pref) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(pref) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(pref) as T
            }
            //upload foto
            modelClass.isAssignableFrom(AnalysisViewModel::class.java) -> {
                AnalysisViewModel(pref) as T
            }
            //upload foto
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}