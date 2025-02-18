package com.example.travelbox.data.repository.post

data class AddPostResponse(
    val success: Boolean,
    val code: String,
    val message: String,
    val result: AddPostResult
)

data class AddPostResult(
    val threadId: Int,
    val imageUrls: List<String>
)