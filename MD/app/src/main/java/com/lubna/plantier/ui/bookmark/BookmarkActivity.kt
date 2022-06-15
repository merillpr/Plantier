package com.lubna.plantier.ui.bookmark

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.WindowInsets
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.lubna.plantier.R
import com.lubna.plantier.databinding.ActivityBookmarkBinding
import com.lubna.plantier.data.model.DataAnalysis

class BookmarkActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookmarkBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookmarkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBookmark()
        setupView()
//        setupAction()
    }

    private fun getDataDummy(): List<DataAnalysis> {
        val data1 = DataAnalysis(
            "Blight",
            "Penyakit 1 adalah bla bla bla",
            "Treatment yang harus dilakukan adalah bla bla bla"
        )
        val data2 = DataAnalysis(
            "Common Rust",
            "Penyakit 2 adalah bla bla bla",
            "Treatment yang harus dilakukan adalah bla bla bla"
        )
        return listOf(data1, data2)
    }

    private fun setupBookmark() {
        val rvBookmark = binding.rvBookmark
        rvBookmark.setHasFixedSize(true)
        rvBookmark.layoutManager = LinearLayoutManager(this)
        val listBookmarkAdapter = BookmarkAdapter(getDataDummy())
        rvBookmark.adapter = listBookmarkAdapter
    }

    private fun setupView() {
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

//    private fun setupAction(){
//        binding.ibBackBookmark.setOnClickListener {
//            finish()
//        }
//    }
}