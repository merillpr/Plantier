package com.lubna.plantier.ui.login

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @field:SerializedName("message")
    var message: String,

    @field:SerializedName("user")
    var user: UsernameLogin
)

data class UsernameLogin(
    @field:SerializedName("username")
    var username:String
)