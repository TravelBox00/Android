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

data class RefreshTokenResponse(
    val result: RefreshTokenResult?,
    val isSuccess: Boolean
)

data class RefreshTokenResult(
    val accessToken: String,
    val refreshToken: String
)

data class LogoutResponse(
    val isSuccess: Boolean
)

data class SignUpResponse(
    val result: SignUpResult?,
    val isSuccess: Boolean
)

data class SignUpResult(
    val userTag: String
)

data class DuplicateResponse(
    val result: Boolean,  // Boolean으로 변경!
    val isSuccess: Boolean
)
