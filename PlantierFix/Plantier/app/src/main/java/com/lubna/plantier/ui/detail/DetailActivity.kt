package com.lubna.plantier.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lubna.plantier.databinding.ActivityDetailBinding
import com.lubna.plantier.databinding.ActivityProfileBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    companion object{
        const val NAME_EXTRA = "name"
        const val DESCRIPTION_EXTRA = "description"
        const val SOLUTION_EXTRA = "solution"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupClick()
        setupDataDetail()

    }

    private fun setupView() {
        supportActionBar?.hide()
    }

    private fun setupClick() {
        binding.icBack.setOnClickListener {
            finish()
        }
    }

    private fun setupDataDetail() {
        val name = intent.getStringExtra(NAME_EXTRA)
        val description = intent.getStringExtra(DESCRIPTION_EXTRA)
        val solution = intent.getStringExtra(SOLUTION_EXTRA)
        binding.tvName.text = name
        binding.tvDescription.text = description
        binding.tvDetailTreatment.text = solution
    }
}