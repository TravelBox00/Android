package com.example.travelbox.data.repository.home

data class PopularPostResponse(
    val page: Int,
    val limit: Int,
    val totalPosts: Int,
    val posts: Array<PostItem>
)

data class PostItem(
    val threadId: Int?,
    val postTitle: String?,
    val postDate: String?,
    val imageURL: String?,
    val totalEngagement: Int?
)