package com.example.travelbox.data.repository.home

data class PopularPostResponse(
    val page: Int,
    val limit: Int,
    val totalPosts: Int,
    val posts: Array<PostItem>
)

data class PostItem(
    val threadId: Int?,
    val postContent: String?,
    val postDate: String?,
    val imageURL: String,
    val userTag : String,
    val totalEngagement: Int?,
    val singInfo: String?
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

// 댓글 추가
data class CommentAddResponse(
    val isSuccess: Boolean,
    val result : Result
)

data class Result(
    val commentId: Int
)

// 댓글 삭제
data class CommentRemoveResponse(

    val isSuccess : Boolean

)

// 댓글 수정
data class CommentFixResponse(

    val isSuccess : Boolean
)

// 게시물 좋아요
data class PostLikeResponse(
    val isSuccess: Boolean,
    val code : String,
    val message : String,
    val result : LikeResult

)

data class LikeResult(
    val isLiked : Boolean
)


// 게시물 북마크
data class PostScrapResponse(
    val isSuccess: Boolean,
    val code : String,
    val message : String,
    val result : ScrapResult
)

data class ScrapResult(

    val isScrapped : Boolean
)


// 지역 필터
data class RegionFilterResponse(
    val result : List<PostData>,
    val isSuccess: Boolean
)

data class PostData(
    val userTag: String,
    val threadId: Int?,
    val imageURL: String?,
    val postTitle: String?,
    val postDate: String?
)