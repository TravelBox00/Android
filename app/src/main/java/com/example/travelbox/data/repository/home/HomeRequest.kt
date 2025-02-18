package com.example.travelbox.data.repository.home


// 댓글 추가
data class CommentRequest(
    val userTag: String,
    val threadId: Int,
    val commentContent: String,
    val commentVisible: String
)


// 게시글 좋아요 상태
data class PostLikeRequest(

    val threadId: Int,
    val userTag: String
)


// 게시글 북마크 상태
data class PostScrapRequest(

    val threadId: Int,
    val userTag: String
)