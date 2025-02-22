package com.example.travelbox.data.repository.auth

import com.example.travelbox.data.repository.my.UserInfoResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AuthInterface {


    // 로그인
    @POST("/users/login")
    fun login(
        @Body request: LoginRequest
    ): Call<LoginResponse>

    // Access Token 재발급
    @POST("/users/login/refresh")
    fun refreshAccessToken(
        @Body request: RefreshTokenRequest
    ): Call<RefreshTokenResponse>

    // 로그아웃
    @DELETE("/users/logout")
    fun logout(): Call<LogoutResponse>

    // 회원가입
    @POST("/users/signup")
    fun signUp(
        @Body request: SignUpRequest
    ): Call<SignUpResponse>

    //ID 중복확인
    @GET("/users/signup/duplicate/{userTag}")
    fun duplicate(
        @Path("userTag") userTag: String
    ): Call<DuplicateResponse>

    // 사용자 정보 수정
    @PATCH("/users/modify")
    fun modify(
        @Body request: ModifyRequest
    ): Call<ModifyResponse>

    // 사용자 정보 조회
    @GET("/users/info")
    fun userInfo() : Call<UserInformaResponse>
}
