package com.lubna.plantier.api

import com.lubna.plantier.ui.analysis.AnalysisResponse
import com.lubna.plantier.ui.login.LoginRequest
import com.lubna.plantier.ui.login.LoginResponse
import com.lubna.plantier.ui.login.SignupResponse
import com.lubna.plantier.ui.signup.SignupRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @POST("signup")
    fun userSignup(
        @Body user: SignupRequest
    ): Call<SignupResponse>

    @POST("login")
    fun userLogin(
        @Body user: LoginRequest
    ): Call<LoginResponse>

    @Multipart
    @POST("predict")
    fun analysis(
        @Part File: MultipartBody.Part
    ): Call<AnalysisResponse>
}