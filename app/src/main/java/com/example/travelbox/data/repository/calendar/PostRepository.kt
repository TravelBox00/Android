package com.example.travelbox.data.repository.calendar

import android.util.Log
import com.example.travelbox.data.network.ApiNetwork
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object PostRepository {

    private val postService: PostInterface = ApiNetwork.createService(PostInterface::class.java)

    fun getUserPosts(userTag: String, callback: (List<PostData>?) -> Unit) {
        val token = ApiNetwork.getAccessToken() ?: return callback(null)  // ✅ 토큰 없으면 실행 안 함

        postService.getUserPosts("Bearer $token", userTag).enqueue(object : Callback<List<PostData>> {
            override fun onResponse(call: Call<List<PostData>>, response: Response<List<PostData>>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    Log.e("PostRepository", "❌ 게시물 조회 실패: ${response.code()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<List<PostData>>, t: Throwable) {
                Log.e("PostRepository", "❌ 네트워크 오류: ${t.message}")
                callback(null)
            }
        })
    }
}