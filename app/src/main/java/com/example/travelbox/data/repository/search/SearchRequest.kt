package com.example.travelbox.data.repository.search

data class SearchTimeRequest(
    val word: String
)

data class AutoCompleteRequest(
    val word: String
)

data class SearchFilterRequest(
    val category: String,
    val region: String
)

data class ThreadSearchRequest(
    val searchKeyword: String,
    val offset: Int
)