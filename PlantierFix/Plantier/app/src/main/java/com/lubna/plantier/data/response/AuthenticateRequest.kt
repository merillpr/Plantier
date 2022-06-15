package com.lubna.plantier.data.response

data class SignupRequest(
    val username: String,
    val password: String,
    val email: String
)

data class LoginRequest(
    val username: String,
    val password: String,
)