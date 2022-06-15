package com.lubna.plantier.ui.detail.disease

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.lubna.plantier.data.model.Disease
import com.lubna.plantier.databinding.ActivityDiseaseBinding

class DiseaseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDiseaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiseaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupClick()
        setupData()
    }

    private fun setupView() {
        supportActionBar?.hide()
    }

    private fun setupClick() {
        binding.icBack.setOnClickListener {
            finish()
        }
    }

    private fun setupData() {
        val disease = intent.getParcelableExtra<Disease>("Disease") as Disease
        binding.apply {
            Glide.with(applicationContext)
                .load(disease.disease_photo)
                .into(imageView)
            tvName.text = disease.disease_name
            tvDetailPenyakit.text = disease.disease_desc
            tvDetailTreatment.text = disease.disease_treat
        }
    }
}