package com.lubna.plantier.ui.signup

import androidx.lifecycle.*
import com.lubna.plantier.api.ApiConfig
import com.lubna.plantier.model.UserPreference
import com.lubna.plantier.ui.login.SignupResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupViewModel(private val pref: UserPreference) : ViewModel() {
    private var _signupResponse = MutableLiveData<SignupResponse>()
    val signupResponse: LiveData<SignupResponse> = _signupResponse

    //signup
    fun userSignup(user: SignupRequest) {
        //setLoading(true)
        val client = ApiConfig.getApiService().userSignup(user)


        client.enqueue(object: Callback<SignupResponse> {
            override fun onResponse(
                call: Call<SignupResponse>,
                response: Response<SignupResponse>,
            ) {
                //setLoading(false)
                if(response.isSuccessful){
                    val responBody = response.body()
                    if(responBody != null) {
                        _signupResponse.value= responBody!!
                        //Toast.makeText(this@SignupActivity, "Account Created", Toast.LENGTH_SHORT).show()
                    }

                }else{
                    _signupResponse.value?.message = "User not created"
                    _signupResponse.value?.User?.username = "none"
                    //Toast.makeText(this@SignupActivity, response.message().toString(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                _signupResponse.value?.message = "On Failure"
                _signupResponse.value?.User?.username = "none"
                //setLoading(false)
                //Toast.makeText(this@SignupActivity, t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }
    //signup
}