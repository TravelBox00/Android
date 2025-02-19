package com.example.travelbox.data.repository.post

import android.util.Log
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class AddPostRepository(private val addPostInterface: AddPostInterface) {

    fun addPost(
        userTag: String,
        postCategory: String,
        postRegionCode: String,
        songName: String,
        postContent: String,
        clothId: Int,
        files: List<File>,
        callback: (AddPostResponse?) -> Unit
    ) {
        // JSON 객체 생성
        val jsonBody = JSONObject().apply {
            put("userTag", userTag)
            put("postCategory", postCategory)
            put("postRegionCode", postRegionCode)
            put("songName", songName)
            put("postContent", postContent)
            put("clothId", clothId)
        }.toString()

        val bodyRequest = jsonBody.toRequestBody("application/json".toMediaTypeOrNull())

        val fileParts = files.map { file ->
            val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("files", file.name, requestFile)
        }

        addPostInterface.addPost(bodyRequest, fileParts)
            .enqueue(object : Callback<AddPostResponse> {
                override fun onResponse(
                    call: Call<AddPostResponse>,
                    response: Response<AddPostResponse>
                ) {
                    if (response.isSuccessful) {
                        callback(response.body())
                    } else {
                        Log.e("AddPost", "서버 오류: ${response.code()}")
                        callback(null)
                    }
                }

                override fun onFailure(call: Call<AddPostResponse>, t: Throwable) {
                    Log.e("AddPost", "네트워크 오류: ${t.localizedMessage}")
                    callback(null)
                }
            })
    }

    fun getSpotifySong(songName: String, callback: (String?) -> Unit) {
        addPostInterface.getSpotifySong(songName).enqueue(object : Callback<SpotifyResponse> {
            override fun onResponse(
                call: Call<SpotifyResponse>,
                response: Response<SpotifyResponse>
            ) {
                if (response.isSuccessful) {
                    callback(response.body()?.trackURL?.spotify)
                } else {
                    callback(null)
                }
            }

            override fun onFailure(call: Call<SpotifyResponse>, t: Throwable) {
                callback(null)
            }
        })
    }

}