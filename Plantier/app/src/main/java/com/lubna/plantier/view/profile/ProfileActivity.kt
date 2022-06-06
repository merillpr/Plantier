package com.lubna.plantier.view.profile

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.lubna.plantier.databinding.ActivityProfileBinding
import com.lubna.plantier.model.UserPreference
import com.lubna.plantier.view.ViewModelFactory
import com.lubna.plantier.view.welcome.WelcomeActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
        setupViewModel()
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
                Log.d("Profile", user.name)
            } else {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
        }
    }
}