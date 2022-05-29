package com.lubna.plantier.view.bookmark

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.lubna.plantier.databinding.ActivityBookmarkBinding
import com.lubna.plantier.model.DataAnalysis

class BookmarkActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookmarkBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookmarkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBookmark()
        setupView()
        setupAction()
    }

    private fun getDataDummy(): List<DataAnalysis>{
        val data1 = DataAnalysis("Penyakit 1", "Penyakit 1 adalah bla bla bla", "Treatment yang harus dilakukan adalah bla bla bla")
        val data2 = DataAnalysis("Penyakit 2", "Penyakit 2 adalah bla bla bla", "Treatment yang harus dilakukan adalah bla bla bla")
        val data:List<DataAnalysis> = listOf(data1,data2)
        return data
    }

    private fun setupBookmark() {
        val rvBookmark = binding.rvBookmark
        rvBookmark.setHasFixedSize(true)
        rvBookmark.layoutManager = LinearLayoutManager(this)
        val listBookmarkAdapter = BookmarkAdapter(getDataDummy())
        rvBookmark.adapter = listBookmarkAdapter
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

    private fun setupAction(){
        binding.ibBackBookmark.setOnClickListener {
            finish()
        }
    }
}