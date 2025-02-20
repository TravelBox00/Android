package com.example.travelbox.data.repository.search

import android.util.Log
import com.example.travelbox.data.network.ApiNetwork
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchRepository {

    companion object {
        private val searchService = ApiNetwork.createService(SearchInterface::class.java)
    }

//    // 시간별 검색된 게시물 요청
//    fun getSearchTimePost(word: String, callback: (SearchResponse?) -> Unit) {
//        searchService.getSearchTimePost(word).enqueue(object : Callback<SearchResponse> {
//
//            override fun onResponse(
//                call: Call<SearchResponse>,
//                response: Response<SearchResponse>
//            ) {
//                if (response.isSuccessful) {
//                    callback(response.body())
//                } else {
//                    Log.e("SearchRepository", "getSearchTimePost 실패: ${response.code()}")
//                    callback(null)
//                }
//            }
//
//            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
//                Log.e("SearchRepository", "getSearchTimePost 통신 실패: ${t.message}", t)
//                callback(null)
//            }
//
//        })
//    }

    // 검색어 자동완성 요청
    fun getSearchWord(word: String, callback: (AutoCompleteResponse?) -> Unit) {
        searchService.getSearchWord(word).enqueue(object : Callback<AutoCompleteResponse> {

            override fun onResponse(
                call: Call<AutoCompleteResponse>,
                response: Response<AutoCompleteResponse>
            ) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    Log.e("SearchRepository", "getSearchWord 실패: ${response.code()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<AutoCompleteResponse>, t: Throwable) {
                Log.e("SearchRepository", "getSearchWord 통신 실패: ${t.message}", t)
                callback(null)
            }

        })
    }

    // 카테고리 & 지역 필터 검색 요청
    fun getSearchFilterPost(category: String, region: String, callback: (SearchResponse?) -> Unit) {
        searchService.getSearchFilterPost(category, region)
            .enqueue(object : Callback<SearchResponse> {

                override fun onResponse(
                    call: Call<SearchResponse>,
                    response: Response<SearchResponse>
                ) {
                    if (response.isSuccessful) {
                        callback(response.body())
                    } else {
                        Log.e(
                            "SearchRepository",
                            "getSearchFilterPost 실패: ${response.code()}"
                        )
                        callback(null)
                    }
                }

                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                    Log.e("SearchRepository", "getSearchFilterPost 통신 실패: ${t.message}", t)
                    callback(null)
                }

            })
    }

    // 검색어에 따른 게시물 조회 요청
    fun getSearchPost(searchKeyword: String, offset: Int, callback: (List<ThreadPost>?) -> Unit) {
        searchService.getSearchPost(searchKeyword, offset)
            .enqueue(object : Callback<List<ThreadPost>> {

                override fun onResponse(
                    call: Call<List<ThreadPost>>,
                    response: Response<List<ThreadPost>>
                ) {
                    if (response.isSuccessful) {
                        val responseData = response.body()
                        Log.d("SearchRepository", "API 응답 데이터: $responseData")
                        callback(responseData)
                    } else {
                        Log.e("SearchRepository", "getSearchPost 실패: ${response.code()}")
                        callback(null)
                    }
                }

                override fun onFailure(call: Call<List<ThreadPost>>, t: Throwable) {
                    Log.e("SearchRepository", "getSearchPost 통신 실패: ${t.message}", t)
                    callback(null)
                }
            })
    }

}