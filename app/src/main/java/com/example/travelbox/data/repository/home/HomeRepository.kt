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

    }

}