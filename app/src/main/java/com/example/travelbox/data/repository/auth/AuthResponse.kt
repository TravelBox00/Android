package com.example.travelbox.data.repository.auth

data class LoginResponse(
    val result: LoginResult?,
    val isSuccess: Boolean
)

data class LoginResult(
    val userTag: String,
    val accessToken: String,
    val refreshToken: String
)