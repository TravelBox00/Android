package com.example.travelbox.data.repository.post

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface AddPostInterface {

    // 게시물 추가
    @Headers("Content-Type: application/json")
    @Multipart
    @POST("/thread/add")
    fun addPost(
        @Part("userTag") userTag: RequestBody,
        @Part("postCategory") postCategory: RequestBody,
        @Part("postRegionCode") postRegionCode: RequestBody,
        @Part("songName") songName: RequestBody,
        @Part("postContent") postContent: RequestBody,
        @Part("clothId") clothId: RequestBody,
        @Part files: List<MultipartBody.Part>
    ): Call<AddPostResponse>

}

