package com.lubna.plantier.ui.login

import androidx.lifecycle.*
import com.lubna.plantier.data.model.UserModel
import com.lubna.plantier.data.model.UserPreference
import com.lubna.plantier.data.remote.ApiConfig
import com.lubna.plantier.data.response.LoginRequest
import com.lubna.plantier.data.response.LoginResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val pref: UserPreference) : ViewModel() {
    private var _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse> = _loginResponse

    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    fun saveUser(user: UserModel) {
        viewModelScope.launch {
            pref.saveUser(user)
        }
    }

    //login
    fun userLogin(user: LoginRequest) {
        //setLoading(true)
        val client = ApiConfig.getApiService().userLogin(user)


        client.enqueue(object: Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>,
            ) {
                //setLoading(false)
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody != null) {
                        _loginResponse.value= responseBody!!
                        //Toast.makeText(this@SignupActivity, "Account Created", Toast.LENGTH_SHORT).show()
                    }

                }else{
                    _loginResponse.value?.message = "Login status unknown"
                    _loginResponse.value?.user?.username = "none"
                    //Toast.makeText(this@SignupActivity, response.message().toString(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _loginResponse.value?.message = "On Failure"
                _loginResponse.value?.user?.username = "none"
                //setLoading(false)
                //Toast.makeText(this@SignupActivity, t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }
    //login
}