package com.lubna.plantier.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.lubna.plantier.R
import com.lubna.plantier.databinding.ActivityMainBinding
import com.lubna.plantier.data.model.Benefit
import com.lubna.plantier.data.model.Disease
import com.lubna.plantier.data.model.UserPreference
import com.lubna.plantier.ui.ViewModelFactory
import com.lubna.plantier.ui.analysis.AnalysisActivity
import com.lubna.plantier.ui.bookmark.BookmarkActivity
import com.lubna.plantier.ui.detail.benefit.BenefitActivity
import com.lubna.plantier.ui.profile.ProfileActivity
import com.lubna.plantier.ui.welcome.WelcomeActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
//    private lateinit var diseaseList: ArrayList<Disease>
    private lateinit var diseaseAdapter: DiseaseAdapter
    private val list = ArrayList<Benefit>()
    private val list1 = ArrayList<Disease>()
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
        list.addAll(listBenefits)
        list1.addAll(listDiseases)
    }

    private fun setupView() {
        supportActionBar?.hide()
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[MainViewModel::class.java]

        mainViewModel.getUser().observe(this) { user ->
            if (user.isLogin) {
                binding.includeProfile.nameTextView.text = user.username
                setUpTheme()
            } else {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
        }
    }

    private fun setUpTheme(){
        mainViewModel.getTheme().observe(this) { isDarkMode: Boolean ->
            if (isDarkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                ProfileActivity.THEME?.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                ProfileActivity.THEME?.isChecked = false
            }
        }
    }

    private fun setupAction() {
        binding.includeAppbar.ibBookmark.setOnClickListener{
            Intent(this, BookmarkActivity::class.java).also {
                startActivity(it)
            }
        }
        binding.includeAppbar.ibProfile.setOnClickListener {
            Intent(this, ProfileActivity::class.java).also {
                startActivity(it)
            }
        }
        binding.includeAppbar.ibCamera.setOnClickListener {
            Intent(this, AnalysisActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    private val listBenefits: ArrayList<Benefit>
        @SuppressLint("Recycle")
        get() {
            val benefitName = resources.getStringArray(R.array.benefit)
            val benefitPhoto = resources.obtainTypedArray(R.array.benefit_photo)
            val benefitDesc = resources.getStringArray(R.array.benefit_desc)
            val listBenefit = ArrayList<Benefit>()
            for (i in benefitName.indices) {
                val benefit = Benefit(benefitName[i],benefitPhoto.getResourceId(i, -1), benefitDesc[i])
                listBenefit.add(benefit)
            }
            return listBenefit
        }

    private fun initDisease(){
        binding.diseaseRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.diseaseRecyclerView.setHasFixedSize(true)

        diseaseAdapter = DiseaseAdapter(list1)
        binding.diseaseRecyclerView.adapter = diseaseAdapter
    }

    private val listDiseases: ArrayList<Disease>
        @SuppressLint("Recycle")
        get() {
            val diseaseName = resources.getStringArray(R.array.disease_name)
            val diseasePhoto = resources.obtainTypedArray(R.array.disease_photo)
            val diseaseDesc = resources.getStringArray(R.array.disease_desc)
            val diseaseTreat = resources.getStringArray(R.array.disease_treat)
            val listDisease = ArrayList<Disease>()
            for (i in diseaseName.indices) {
                val disease = Disease(diseaseName[i],diseasePhoto.getResourceId(i, -1), diseaseDesc[i], diseaseTreat[i])
                listDisease.add(disease)
            }
            return listDisease
        }

    private fun initBenefit(){
        binding.benefitRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false )
        binding.benefitRecyclerView.setHasFixedSize(true)
        val snapHelper : SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.benefitRecyclerView)

        benefitAdapter = BenefitAdapter(list)
        binding.benefitRecyclerView.adapter = benefitAdapter
        benefitAdapter.setOnItemClickCallback(object : BenefitAdapter.OnItemClickCallback {
            override fun onItemClick(data: Benefit) {
                Intent(this@MainActivity, BenefitActivity::class.java).also {
                    it.putExtra(BenefitActivity.EXTRA_BENEFIT_DETAIL, data)
                    startActivity(it)
                }
            }
        })
    }
}
