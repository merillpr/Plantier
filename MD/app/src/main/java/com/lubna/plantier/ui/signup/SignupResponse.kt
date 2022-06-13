package com.lubna.plantier.ui.login

import com.google.gson.annotations.SerializedName

data class SignupResponse(
    @field:SerializedName("message")
    var message: String,

    @field:SerializedName("User")
    var User: UsernameSignup
)

data class UsernameSignup(
    @field:SerializedName("username")
    var username:String
)