package com.lubna.plantier.view.preview

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.lubna.plantier.databinding.ActivityPreviewBinding
import com.lubna.plantier.utils.rotateBitmap
import com.lubna.plantier.view.camera.CameraActivity
import java.io.File

class PreviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPreviewBinding

    companion object{
        const val CAMERA_X_RESULT = 200
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.retake.setOnClickListener{startCameraX()}
        binding.analysis.setOnClickListener{startAnalysis()}
    }

    private fun startAnalysis(){

    }

    private fun startCameraX() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = it.data?.getSerializableExtra("picture") as File
            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean

            val result = rotateBitmap(
                BitmapFactory.decodeFile(myFile.path),
                isBackCamera
            )

            binding.ivImagePreview.setImageBitmap(result)
        }
    }


}