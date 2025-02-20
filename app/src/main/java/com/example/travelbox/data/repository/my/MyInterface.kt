package com.example.travelbox.data.repository.my
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MyInterface {

    //팔로워
    @GET("/follow/showFollower/{userTag}")
    fun getFollowers(
        @Path("userTag") userTag: String
    ): Call<FollowerResponse>


    //팔로잉
    @GET("follow/showFollowing/{userTag}")
    fun getFollowing(@Path("userTag") userTag: String)
    : Call<FollowerResponse>

    //게시글
    @GET("/thread/specific")
    suspend fun getPosts()
    : Response<ThreadResponse>

    //나의 댓글
    @GET("/comment/info")
    fun fetchComments()
    : Call<CommentResponse>

    //회원 정보
    /*
    @POST("/users/modify")
    fun modifyUser(@Body userInfo: Map<String, String>)
    : Call<UserResponse>
     */

    //여행기록
    @GET("/thread/my")
    suspend fun getMyThreads(): Response<StoryResponse>

}
