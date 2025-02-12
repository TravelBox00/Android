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


data class PostCommentResponse(
    val isSuccess: Boolean,
    val result : List<Comment>

)


data class Comment(
    val commentId: Int,
    val commentContent: String?,
    val commentVisible: String?,
    val commenterNickname: String?
)