package com.example.travelbox.data.repository.auth

data class LoginRequest(
    val userTag: String,
    val userPassword: String
)

data class RefreshTokenRequest(
    val userTag: String,
    val refreshToken: String
)

data class SignUpRequest(
    val userTag: String,
    val userPassword: String,
    val userNickname: String
)

