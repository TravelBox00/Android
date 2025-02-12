package com.example.travelbox.data.repository.home

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface HomeInterface {

    // 인기 게시물 조회
    @GET("/thread/popular")
    fun getPopularPost(

        @Query("page") page : Int,
        @Query("limit") limit : Int

    ) : Call<List<PostItem>>


    // 인기(특정) 게시글 댓글 조회
    @GET("/comment/show")
    fun getPostComment(

        @Query("threadId") threadId : Int

    ) : Call<PostCommentResponse>


    // 댓글 추가
    @POST("/comment/add")
    fun addPostComment(
        @Query("userId") userId : Int,
        @Query("threadId") threadId : Int,
        @Query("commentContent") commentContent : String,
        @Query("commentVisible") commentVisible : String
    ) : Call<CommentAddResponse>


}