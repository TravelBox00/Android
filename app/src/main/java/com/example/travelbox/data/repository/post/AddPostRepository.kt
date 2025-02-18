package com.example.travelbox.data.repository.post

import android.util.Log
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
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
        val userTagBody = userTag.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val postCategoryBody = postCategory.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val postRegionCode = postRegionCode.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val songName = songName.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val postContentBody = postContent.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val clothIdBody = clothId.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())

        val fileParts = files.mapIndexed { index, file ->
            val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("files[]", file.name, requestFile)
        }

        addPostInterface.addPost(
            userTagBody, postCategoryBody, postRegionCode, songName, postContentBody, clothIdBody, fileParts
        ).enqueue(object : Callback<AddPostResponse> {
            override fun onResponse(call: Call<AddPostResponse>, response: Response<AddPostResponse>) {
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
}