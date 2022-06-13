package com.lubna.plantier.ui.analysis

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lubna.plantier.api.ApiConfig
import com.lubna.plantier.model.UserPreference
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AnalysisViewModel(private val pref: UserPreference): ViewModel() {
    private var _analysisResponse = MutableLiveData<AnalysisResponse>()
    val analysisResponse: LiveData<AnalysisResponse> = _analysisResponse

    //upload foto
    fun analysis(imageMultipart: MultipartBody.Part) {
        //setLoading(true)
        val client = ApiConfig.getApiService().analysis(imageMultipart)
        client.enqueue(object : Callback<AnalysisResponse> {
            override fun onResponse(
                call: Call<AnalysisResponse>,
                response: Response<AnalysisResponse>
            ) {
                //setLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _analysisResponse.value = responseBody!!
                        //Log.e("AnalysisActivity",responseBody.toString())
                    }
                }else{
                    _analysisResponse.value?.message = "Failed to Upload Image"
                }
            }
            override fun onFailure(call: Call<AnalysisResponse>, t: Throwable) {
                _analysisResponse.value?.message = "On Failure"
            }
        })
    }
    //upload foto
}