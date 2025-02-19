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
                        Log.e("인기 게시물 조회", "통신 실패 ${error}")
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

        // 댓글 추가
        fun postCommentAdd(userTag: String, threadId: Int, commentContent : String, commentVisible : String, callback: (CommentAddResponse?) -> Unit) {


            val requestBody = CommentRequest(
                userTag = userTag,
                threadId = threadId,
                commentContent = commentContent,
                commentVisible = commentVisible
            )

            val call = homeService.addPostComment(requestBody)

            call.enqueue(object : Callback<CommentAddResponse> {

                override fun onResponse(
                    call: Call<CommentAddResponse>,
                    response: Response<CommentAddResponse>
                ) {
                    if (response.isSuccessful) {

                        // 통신 성공
                        Log.d("댓글 추가", "통신 성공 ${response.code()} ")
                        callback(response.body())

                    } else {
                        // 통신 실패
                        val error = response.errorBody()?.string()
                        Log.e("댓글 추가", "통신 실패 $error")
                        callback(null)

                    }
                }

                override fun onFailure(call: Call<CommentAddResponse>, t: Throwable) {

                    // 통신 실패
                    Log.w("댓글 추가", "통신 실패1: ${t.message}")
                    callback(null)
                }

            })
        }

        // 댓글 삭제
        fun postCommentRemove(commentId : Int,  callback: (CommentRemoveResponse?) -> Unit) {

            Log.d("댓글 삭제", "삭제할 댓글 ID3: $commentId")


            val call = homeService.removePostComment(commentId)

            call.enqueue(object : Callback<CommentRemoveResponse> {
                override fun onResponse(
                    call: Call<CommentRemoveResponse>,
                    response: Response<CommentRemoveResponse>
                ) {
                    if (response.isSuccessful) {

                        // 통신 성공
                        Log.d("댓글 삭제", "통신 성공 ${response.code()} ")
                        callback(response.body())

                    } else {
                        // 통신 실패
                        val error = response.errorBody()?.string()
                        Log.e("댓글 삭제", "통신 실패 $error")
                        callback(null)

                    }
                }

                override fun onFailure(call: Call<CommentRemoveResponse>, t: Throwable) {
                    // 통신 실패
                    Log.w("댓글 삭제", "통신 실패1: ${t.message}")
                    callback(null)
                }
            })
        }


        // 댓글 수정
        fun postCommentFix(commentFixRequest: CommentFixRequest, callback: (CommentFixResponse?) -> Unit) {

            Log.d("댓글 삭제", "삭제할 댓글 ID3: ${commentFixRequest.commentId}")


            val call = homeService.fixPostComment(commentFixRequest)

            call.enqueue(object : Callback<CommentFixResponse> {
                override fun onResponse(
                    call: Call<CommentFixResponse>,
                    response: Response<CommentFixResponse>
                ) {
                    if (response.isSuccessful) {

                        // 통신 성공
                        Log.d("댓글 수정", "통신 성공 ${response.code()} ")
                        callback(response.body())

                    } else {
                        // 통신 실패
                        val error = response.errorBody()?.string()
                        Log.e("댓글 수정", "통신 실패 $error")
                        callback(null)

                    }
                }

                override fun onFailure(call: Call<CommentFixResponse>, t: Throwable) {
                    // 통신 실패
                    Log.w("댓글 수정", "통신 실패1: ${t.message}")
                    callback(null)
                }
            })
        }

        // 게시물 좋아요
        fun postAddIsLiked(threadId: Int, userTag: String, callback: (PostLikeResponse?) -> Unit) {

            val requestBody = PostLikeRequest(

                threadId = threadId,
                userTag = userTag

            )

            val call = homeService.postIsLiked(requestBody)

            call.enqueue(object : Callback<PostLikeResponse> {
                override fun onResponse(
                    call: Call<PostLikeResponse>,
                    response: Response<PostLikeResponse>
                ) {
                    if (response.isSuccessful) {

                        // 통신 성공
                        Log.d("게시물 좋아요", "통신 성공 ${response.code()} ")
                        callback(response.body())

                    } else {
                        // 통신 실패
                        val error = response.errorBody()?.string()
                        Log.e("게시물 좋아요", "통신 실패 $error")
                        callback(null)
                    }

                }

                override fun onFailure(call: Call<PostLikeResponse>, t: Throwable) {
                    // 통신 실패
                    Log.w("게시물 좋아요", "통신 실패1: ${t.message}")
                    callback(null)
                }
            })

        }

        // 게시물 북마크
        fun postAddIsScrapped(threadId: Int, userTag: String, callback: (PostScrapResponse?) -> Unit) {

            val requestBody = PostScrapRequest(

                threadId = threadId,
                userTag = userTag

            )

            val call = homeService.postIsScrapped(requestBody)

            call.enqueue(object : Callback<PostScrapResponse> {
                override fun onResponse(
                    call: Call<PostScrapResponse>,
                    response: Response<PostScrapResponse>
                ) {
                    if (response.isSuccessful) {

                        // 통신 성공
                        Log.d("게시물 북마크", "통신 성공 ${response.code()} ")
                        callback(response.body())

                    } else {
                        // 통신 실패
                        val error = response.errorBody()?.string()
                        Log.e("게시물 북마크", "통신 실패 $error")
                        callback(null)
                    }

                }

                override fun onFailure(call: Call<PostScrapResponse>, t: Throwable) {
                    // 통신 실패
                    Log.w("게시물 북마크", "통신 실패1: ${t.message}")
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
                        Log.d("지역 필터링", "통신 성공 ${response.code()} ")
                        callback(response.body())

                    } else {
                        // 통신 실패
                        val error = response.errorBody()?.string()
                        Log.e("지역 필터링", "통신 실패 $error")
                        callback(null)

                    }
                }

                override fun onFailure(call: Call<RegionFilterResponse>, t: Throwable) {

                    // 통신 실패
                    Log.w("지역 필터링", "통신 실패1: ${t.message}")
                    callback(null)
                }
            })



        }

    }

}