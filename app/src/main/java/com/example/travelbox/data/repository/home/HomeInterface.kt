package com.example.travelbox.data.repository.home

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
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

        @Body request: CommentRequest

    ) : Call<CommentAddResponse>

    // 댓글 삭제
    @DELETE("/comment/remove")
    fun removePostComment(


        @Query("commentId") commentId : Int,



    )   : Call<CommentRemoveResponse>


    // 댓글 수정
    @PUT("/comment/fix")
    fun fixPostComment(
        @Body request : CommentFixRequest
    ) : Call<CommentFixResponse>


    // 게시글 좋아요 상태
    @POST("/thread/like")
    fun postIsLiked(

        @Body request : PostLikeRequest

    ) : Call<PostLikeResponse>


    // 게시글 북마크 상태
    @POST("/thread/scrap")
    fun postIsScrapped(

        @Body request : PostScrapRequest

    ) : Call<PostScrapResponse>



    // 지역 필터
    @GET("/search/filter")
    fun regionFilter(
        @Query("category") category : String,
        @Query("region") region : String?
    ) : Call<RegionFilterResponse>


}