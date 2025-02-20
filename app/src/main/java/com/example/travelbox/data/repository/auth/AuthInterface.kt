package com.example.travelbox.data.repository.auth

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthInterface {


    // 로그인
    @POST("/users/login")
    fun login(
        @Body request: LoginRequest
    ): Call<LoginResponse>
}