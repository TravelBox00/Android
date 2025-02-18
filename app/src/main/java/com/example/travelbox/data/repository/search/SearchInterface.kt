package com.example.travelbox.data.repository.search

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchInterface {

    // 시간별 검색된 게시물 반환
//    @GET("/search")
//    fun getSearchTimePost(
//        @Query("word") word: String
//    ): Call<SearchResponse>

    // 검색어 입력 시 자동완성 단어 반환
    @GET("/search/word")
    fun getSearchWord(
        @Query("word") word: String
    ): Call<AutoCompleteResponse>

    // 카테고리 & 지역 필터 검색
    @GET("/search/filter")
    fun getSearchFilterPost(
        @Query("category") category: String,
        @Query("region") region: String
    ): Call<SearchResponse>

    // 검색어에 따른 게시물 조회
    @GET("/thread/search")
    fun getSearchPost(
        @Query("searchKeyword") searchKeyword: String,
        @Query("offset") offset: Int
    ): Call<List<ThreadPost>>
}