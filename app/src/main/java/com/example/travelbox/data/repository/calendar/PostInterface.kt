package com.example.travelbox.data.repository.calendar

import com.example.travelbox.data.repository.calendar.PostData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface PostInterface {

    @GET("/thread/specific")
    fun getUserPosts(
        @Header("Authorization") token: String,  // ✅ JWT 인증 추가
        @Query("userTag") userTag: String        // ✅ userTag 기반 게시물 조회
    ): Call<List<PostData>>
}