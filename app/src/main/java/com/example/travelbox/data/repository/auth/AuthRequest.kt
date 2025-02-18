package com.example.travelbox.data.repository.auth

data class LoginRequest(
    val userTag: String,
    val userPassword: String
)