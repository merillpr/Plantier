package com.lubna.plantier.view.main

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.lubna.plantier.R
import com.lubna.plantier.databinding.ActivityMainBinding
import com.lubna.plantier.model.Benefit
import com.lubna.plantier.model.Disease
import com.lubna.plantier.model.UserPreference
import com.lubna.plantier.view.ViewModelFactory
import com.lubna.plantier.view.bookmark.BookmarkActivity
import com.lubna.plantier.view.camera.CameraActivity
import com.lubna.plantier.view.profile.ProfileActivity
import com.lubna.plantier.view.welcome.WelcomeActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var diseaseList: ArrayList<Disease>
    private lateinit var diseaseAdapter: DiseaseAdapter
    private lateinit var benefitList: ArrayList<Benefit>
    private lateinit var benefitAdapter: BenefitAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        setupAction()
        initBenefit()
        initDisease()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[MainViewModel::class.java]

        mainViewModel.getUser().observe(this) { user ->
            if (user.isLogin) {
                binding.includeProfile.nameTextView.text = user.name
            } else {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
        }
    }

    private fun setupAction() {

        binding.includeProfile.logoutButton.setOnClickListener {
            mainViewModel.logout()
        }
        binding.includeAppbar.ibBookmark.setOnClickListener{
            Intent(this, BookmarkActivity::class.java).also {
                startActivity(it)
            }
        }
        binding.includeAppbar.ibSetting.setOnClickListener {
            Intent(this, ProfileActivity::class.java).also {
                startActivity(it)
            }
        }
        binding.includeAppbar.ibCamera.setOnClickListener {
            Intent(this, CameraActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    private fun initDisease(){
        binding.diseaseRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.diseaseRecyclerView.setHasFixedSize(true)

        diseaseList = ArrayList()
        diseaseDummyData()

        diseaseAdapter = DiseaseAdapter(diseaseList)
        binding.diseaseRecyclerView.adapter = diseaseAdapter
    }

    private fun diseaseDummyData(){
        diseaseList.add(Disease(R.drawable.pic1, "Bulai", getString(R.string.desc1), getString(R.string.prevent1)))
        diseaseList.add(Disease(R.drawable.pic2, "Bercak Daun", getString(R.string.desc2), getString(R.string.prevent2)))
    }

    private fun initBenefit(){
        binding.benefitRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false )
        binding.benefitRecyclerView.setHasFixedSize(true)
        val snapHelper : SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.benefitRecyclerView)

        benefitList = ArrayList()
        benefitDummyData()

        benefitAdapter = BenefitAdapter(benefitList)
        binding.benefitRecyclerView.adapter = benefitAdapter
    }

    private fun benefitDummyData(){
        benefitList.add(Benefit(R.drawable.ic_stomach, "Melancarkan pencernaan"))
        benefitList.add(Benefit(R.drawable.ic_eye, "Menyehatkan mata"))
        benefitList.add(Benefit(R.drawable.ic_bone, "Meningkatkan kepadatan tulang"))
        benefitList.add(Benefit(R.drawable.ic_depression, "Mencegah depresi"))
        benefitList.add(Benefit(R.drawable.ic_blood, "Mengendalikan tekanan darah"))
        benefitList.add(Benefit(R.drawable.ic_disease, "Menangkal radikal bebas"))
        benefitList.add(Benefit(R.drawable.ic_heart, "Menjaga kesehatan jantung"))
        benefitList.add(Benefit(R.drawable.ic_lungs, "Mencegah kanker paru-paru"))
        benefitList.add(Benefit(R.drawable.ic_brain, "Meningkatkan daya ingat"))
        benefitList.add(Benefit(R.drawable.ic_energy, "Sumber energi"))
        benefitList.add(Benefit(R.drawable.ic_molecul, "Kaya antioksidan"))
    }

//    private fun playAnimation() {
//        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
//            duration = 6000
//            repeatCount = ObjectAnimator.INFINITE
//            repeatMode = ObjectAnimator.REVERSE
//        }.start()
//
//        val name = ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 1f).setDuration(500)
//        val message = ObjectAnimator.ofFloat(binding.messageTextView, View.ALPHA, 1f).setDuration(500)
//        val logout = ObjectAnimator.ofFloat(binding.logoutButton, View.ALPHA, 1f).setDuration(500)
//
//        AnimatorSet().apply {
//            playSequentially(name, message, logout)
//            startDelay = 500
//        }.start()
//    }
}
