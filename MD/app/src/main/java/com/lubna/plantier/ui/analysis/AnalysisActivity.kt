package com.lubna.plantier.ui.analysis

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.lubna.plantier.R
import com.lubna.plantier.data.model.UserPreference
import com.lubna.plantier.databinding.ActivityAnalysisBinding
import com.lubna.plantier.ui.ViewModelFactory
import com.lubna.plantier.ui.detail.DetailActivity
import com.lubna.plantier.utils.customTempFile
import com.lubna.plantier.utils.reduceFileImage
import com.lubna.plantier.utils.uriToFile
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

private var imageFile: File? = null
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class AnalysisActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var analysisViewModel: AnalysisViewModel
    private lateinit var binding: ActivityAnalysisBinding
    private lateinit var currentPhotoPath: String

    companion object {
        private val PERMISSIONS_REQUIRED = arrayOf(Manifest.permission.CAMERA)
        private const val PERMISSIONS_CODE_REQUEST = 10
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantedResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantedResults)
        if (requestCode == PERMISSIONS_CODE_REQUEST) {
            if (!allPermissionsAllowed()) {
                Toast.makeText(this,
                    "Not received permission",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsAllowed() = PERMISSIONS_REQUIRED.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnalysisBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!allPermissionsAllowed()) {
            ActivityCompat.requestPermissions(
                this,
                PERMISSIONS_REQUIRED,
                PERMISSIONS_CODE_REQUEST
            )
        }

        setupView()
        setupViewModel()
        binding.btnGallery.setOnClickListener(this)
        binding.btnCamera.setOnClickListener(this)
        binding.btnUpload.setOnClickListener(this)
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

    private fun setupViewModel() {
        analysisViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[AnalysisViewModel::class.java]
    }

    override fun onClick(p0: View) {
        when(p0.id){
            R.id.btn_gallery -> galleryClicked()
            R.id.btn_camera -> cameraClicked()
            R.id.btn_upload -> uploadClicked() //upload foto
        }
    }

    private fun galleryClicked() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Chosee your image")
        intentGallery.launch(chooser)
    }

    private val intentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val imageGallery: Uri = result.data?.data as Uri
            val file = uriToFile(imageGallery, this@AnalysisActivity)
            imageFile = file
            binding.preview.setImageURI(imageGallery)
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun cameraClicked() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)

        customTempFile(application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this@AnalysisActivity,
                "com.lubna.plantier",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            intentCamera.launch(intent)
        }
    }

    private val intentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val file = File(currentPhotoPath)
            imageFile = file
            val result = BitmapFactory.decodeFile(file.path)
            binding.preview.setImageBitmap(result)
        }
    }

    private fun uploadClicked() {
        //Toast.makeText(this@AnalysisActivity, "clicked", Toast.LENGTH_SHORT).show()
        //Log.e("AnalysisActivity", "clicked")
        if (imageFile != null) {
            //Toast.makeText(this@AnalysisActivity, "image tersedia", Toast.LENGTH_SHORT).show()
            //Log.e("AnalysisActivity", "image tersedia")
            val image = reduceFileImage(imageFile as File)
            //val desc = binding.etDescription.text.toString()
            //val description = desc.toRequestBody("text/plain".toMediaType())
            val requestImage = image.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part
                .createFormData(
                    "images",
                    image.name,
                    requestImage
                )
            analysisViewModel.analysis(imageMultipart)
            analysisViewModel.analysisResponse.observe(this){ response ->
                if (response?.message == "prediction success") {
                    //Log.e("AnalysisActivity", analysisResponse.toString())
                    //showPreview(response.Model.name, response.Model.description, response.Model.solution)
                    Toast.makeText(this@AnalysisActivity, response.Model.description, Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, DetailActivity::class.java).apply {
                        putExtra(DetailActivity.NAME_EXTRA, response.Model.name)
                        putExtra(DetailActivity.DESCRIPTION_EXTRA, response.Model.description)
                        putExtra(DetailActivity.SOLUTION_EXTRA, response.Model.solution)
                    }
                    startActivity(intent)
                } else {
                    Toast.makeText(this@AnalysisActivity, response?.message, Toast.LENGTH_SHORT).show()
                }
            }

        } else {
            Toast.makeText(this@AnalysisActivity,
                "Add the image, please!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    //upload foto
    private fun showPreview(nameDisease: String?, descDisease: String?, solDisease: String?){
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_detail_analysis)

        val name = dialog.findViewById<TextView>(R.id.tv_name)
        val desc = dialog.findViewById<TextView>(R.id.tv_detail_penyakit)
        val sol = dialog.findViewById<TextView>(R.id.tv_detail_treatment)

        name.text = nameDisease
        desc.text = descDisease
        sol.text = solDisease

        dialog.show()
    }
}