package com.lubna.plantier.data.remote

import com.lubna.plantier.data.response.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

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