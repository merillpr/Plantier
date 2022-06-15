package com.lubna.plantier.data.response

import com.google.gson.annotations.SerializedName

data class SignupResponse(
    @field:SerializedName("message")
    var message: String,

    @field:SerializedName("User")
    var User: UsernameAuth
)

data class UsernameAuth(
    @field:SerializedName("username")
    var username:String
)

data class LoginResponse(
    @field:SerializedName("message")
    var message: String,

    @field:SerializedName("user")
    var user: UsernameAuth
)
