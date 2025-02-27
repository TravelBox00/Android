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
    val isSuccess: Boolean
)

data class DuplicateResponse(
    val result: Boolean,  // Boolean으로 변경!
    val isSuccess: Boolean
)

data class ModifyResponse(
    val isSuccess: Boolean
)

data class UserInformaResponse(
    val result: UserInformaResult?,
    val isSuccess: Boolean
)

data class UserInformaResult(
    val userId: String,
    val email: String,
    val name: String
)