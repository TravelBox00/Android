package com.example.travelbox.data.repository.home

import android.util.Log
import com.example.travelbox.data.network.ApiNetwork
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeRepository {

    companion object {


        private val homeService = ApiNetwork.createService(HomeInterface::class.java)

        // 인기 게시물 조회
        fun getPopularPost(page: Int, limit: Int, callback: (List<PostItem>?) -> Unit) {

            val call = homeService.getPopularPost(page, limit)

            call.enqueue(object : Callback<List<PostItem>> {

                override fun onResponse(
                    call: Call<List<PostItem>>,
                    response: Response<List<PostItem>>
                ) {
                    if (response.isSuccessful) {

                        // 통신 성공
                        Log.d("인기 게시물 조회", "통신 성공 ${response.code()} ")

                        callback(response.body())
                    } else {

                        // 통신 실패
                        val error = response.errorBody()?.toString()
                        Log.e("인기 게시물 조회", "통신 실패 $error")
                        callback(null)
                    }
                }

                override fun onFailure(call: Call<List<PostItem>>, t: Throwable) {
                    // 통신 실패
                    Log.w("인기 게시물 조회", "통신 실패: ${t.message}")
                    callback(null)
                }

            })
        }


        // 특정 게시물 댓글 조회
        fun getPostComment(threadId: Int, callback: (PostCommentResponse?) -> Unit) {

            val call = homeService.getPostComment(threadId)

            call.enqueue(object : Callback<PostCommentResponse> {
                override fun onResponse(
                    call: Call<PostCommentResponse>,
                    response: Response<PostCommentResponse>
                ) {
                    if (response.isSuccessful) {

                        // 통신 성공
                        Log.d("게시물 댓글 조회", "통신 성공 ${response.code()} ")
                        callback(response.body())

                    } else {
                        // 통신 실패
                        val error = response.errorBody()?.toString()
                        Log.e("게시물 댓글 조회", "통신 실패 $error")
                        callback(null)

                    }
                }

                override fun onFailure(call: Call<PostCommentResponse>, t: Throwable) {

                    // 통신 실패
                    Log.w("게시물 댓글 조회", "통신 실패: ${t.message}")
                    callback(null)
                }
            })
        }

        // 지역 필터 검색
        fun regionFilterSearch(category : String, region : String, callback : (RegionFilterResponse?) -> Unit) {

            val call = homeService.regionFilter(category, region)

            call.enqueue(object : Callback<RegionFilterResponse> {
                override fun onResponse(
                    call: Call<RegionFilterResponse>,
                    response: Response<RegionFilterResponse>
                ) {
                    if (response.isSuccessful) {

                        // 통신 성공
                        Log.d("게시물 댓글 조회", "통신 성공 ${response.code()} ")
                        callback(response.body())

                    } else {
                        // 통신 실패
                        val error = response.errorBody()?.string()
                        Log.e("게시물 댓글 조회", "통신 실패 $error")
                        callback(null)

                    }
                }

                override fun onFailure(call: Call<RegionFilterResponse>, t: Throwable) {

                    // 통신 실패
                    Log.w("게시물 댓글 조회", "통신 실패1: ${t.message}")
                    callback(null)
                }
            })



        }

    }

}