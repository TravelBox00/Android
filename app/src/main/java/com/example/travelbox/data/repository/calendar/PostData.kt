package com.example.travelbox.data.repository.calendar

data class PostData(
    val imageURL: String?,   // 게시물 대표 이미지 (nullable)
    val postContent: String, // 게시물 내용
    val postDate: String,    // 게시물 작성 날짜 (ISO 8601 형식)
    val userTag: String,     // 작성자 태그
    val threadId: Int        // 게시물 ID
)
