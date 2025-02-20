package com.example.travelbox.data.repository.search

data class SearchPost(
    val threadId: Int,
    val postImageURL: String,
    val postTitle: String,
    val postDate: String
)

data class SearchResponse(
    val result: List<SearchPost>,
    val isSuccess: Boolean
)

data class AutoCompleteResponse(
    val result: List<String>,
    val isSuccess: Boolean
)

data class ThreadPost(
    val userTag: String,
    val threadId: Int,
    val postContent: String,
    val postDate: String,
    val imageURL: String,
    val clothInfo: String,
    val singInfo: String
)

data class ThreadSearchResponse(
    val posts: List<ThreadPost>,
    val totalResults: Int
)
