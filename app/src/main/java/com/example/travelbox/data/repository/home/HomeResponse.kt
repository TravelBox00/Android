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
    val imageURL: String,
    val totalEngagement: Int?
)

// 댓글 작성
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


data class CommentAddResponse(
    val isSuccess: Boolean,
    val result : Result
)

data class Result(
    val commentId: Int
)


// 지역 필터
data class RegionFilterResponse(
    val result : List<PostData>,
    val isSuccess: Boolean
)

data class PostData(
    val threadId: Int?,
    val postImageURL: String?,
    val postTitle: String?,
    val postDate: String?
)