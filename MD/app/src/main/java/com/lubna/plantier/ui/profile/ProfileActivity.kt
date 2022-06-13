package com.lubna.plantier.ui.profile

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.switchmaterial.SwitchMaterial
import com.lubna.plantier.R
import com.lubna.plantier.databinding.ActivityProfileBinding
import com.lubna.plantier.model.UserPreference
import com.lubna.plantier.ui.ViewModelFactory
import com.lubna.plantier.ui.welcome.WelcomeActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var profileViewModel: ProfileViewModel
    private var switchTheme: SwitchMaterial? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        switchTheme = binding.theme
        THEME = switchTheme

        setupViewModel()
        setUpTheme()
        setupAction()
    }

    private fun setupAction(){
        binding.ibBackProfile.setOnClickListener {
            finish()
        }
        binding.logoutButton.setOnClickListener {
            profileViewModel.logout()
        }
    }

    private fun setupViewModel() {

        profileViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[ProfileViewModel::class.java]

        profileViewModel.getUser().observe(this) { user ->
            if (user.isLogin) {
                Log.d("Profile", user.username)
            } else {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
        }
    }

    private fun setUpTheme() {
        profileViewModel.getTheme().observe(this) { isDarkMode: Boolean ->
            if (isDarkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme?.isChecked = true
                binding.tvTheme.text = getString(R.string.bright_mode)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme?.isChecked = false
                binding.tvTheme.text = getString(R.string.dark_mode)
            }
        }

        switchTheme?.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            profileViewModel.saveTheme(isChecked)
        }
    }

        companion object{
        var THEME: SwitchMaterial? = null
    }
}