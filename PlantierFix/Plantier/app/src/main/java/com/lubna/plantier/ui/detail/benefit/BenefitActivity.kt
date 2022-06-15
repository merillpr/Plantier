package com.lubna.plantier.ui.detail.benefit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.lubna.plantier.R
import com.lubna.plantier.data.model.Benefit
import com.lubna.plantier.databinding.ActivityBenefitBinding

class BenefitActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBenefitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBenefitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val benefit = intent.getParcelableExtra<Benefit>(EXTRA_BENEFIT_DETAIL) as Benefit

        binding.apply {
            ivBenefit.setImageResource(benefit.benefit_photo)
            tvName.text = benefit.benefit
            tvDesc.text = benefit.benefit_desc
        }

        setupView()
        setupClick()
    }

    private fun setupView() {
        supportActionBar?.hide()
    }

    private fun setupClick() {
        binding.icBack.setOnClickListener {
            finish()
        }
    }

    companion object{
        const val EXTRA_BENEFIT_DETAIL = "extra_benefit_detail"
    }
}