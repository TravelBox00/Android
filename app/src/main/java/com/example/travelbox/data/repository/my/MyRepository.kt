package com.example.travelbox.data.repository.my

import android.util.Log
import com.example.travelbox.data.network.ApiNetwork
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyRepository {

    companion object {
        private val myService = ApiNetwork.createService(MyInterface::class.java)

        // 팔로워
        fun getFollowers(userTag: String, callback: (List<FollowerItem>?) -> Unit) {
            myService.getFollowers(userTag).enqueue(object : Callback<FollowerResponse> {
                override fun onResponse(
                    call: Call<FollowerResponse>,
                    response: Response<FollowerResponse>
                ) {
                    if (response.isSuccessful) {
                        val followers = response.body()?.followers
                        Log.d("MyRepository", "팔로워 목록 조회 성공: $followers")
                        callback(followers)
                    } else {
                        Log.e("MyRepository", "팔로워 목록 조회 실패: ${response.errorBody()?.string()}")
                        callback(null)
                    }
                }

                override fun onFailure(call: Call<FollowerResponse>, t: Throwable) {
                    Log.e("MyRepository", "팔로워 목록 요청 실패: ${t.message}")
                    callback(null)
                }
            })
        }

        fun getFollowing(userTag: String, callback: (List<FollowingItem>?) -> Unit) {
            myService.getFollowing(userTag).enqueue(object : Callback<FollowingResponse> {
                override fun onResponse(
                    call: Call<FollowingResponse>,
                    response: Response<FollowingResponse>
                ) {
                    if (response.isSuccessful) {
                        val followings = response.body()?.followings
                        Log.d("MyRepository", "팔로잉 목록 조회 성공: $followings")
                        callback(followings)
                    } else {
                        Log.e("MyRepository", "팔로잉 목록 조회 실패: ${response.errorBody()?.string()}")
                        callback(null)
                    }
                }

                override fun onFailure(call: Call<FollowingResponse>, t: Throwable) {
                    Log.e("MyRepository", "팔로잉 목록 요청 실패: ${t.message}")
                    callback(null)
                }
            })
        }

        // 게시글 조회
        fun fetchPosts(callback: (List<Post>?) -> Unit) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = myService.getPosts()
                    if (response.isSuccessful) {
                        val posts = response.body()?.posts ?: emptyList()
                        // UI 업데이트는 메인 스레드에서 해야 함
                        withContext(Dispatchers.Main) {
                            callback(posts)
                        }
                    } else {
                        Log.e("MyRepository", "게시글 조회 실패: ${response.message()}")
                        withContext(Dispatchers.Main) {
                            callback(null)
                        }
                    }
                } catch (e: Exception) {
                    Log.e("MyRepository", "게시글 조회 중 오류 발생: ${e.message}")
                    withContext(Dispatchers.Main) {
                        callback(null)
                    }
                }
            }
        }

        // 나의 댓글 조회
        fun fetchComments(callback: (List<Comment>?) -> Unit) {
            myService.fetchComments().enqueue(object : Callback<CommentResponse> {
                override fun onResponse(
                    call: Call<CommentResponse>,
                    response: Response<CommentResponse>
                ) {
                    if (response.isSuccessful && response.body()?.isSuccess == true) {
                        response.body()?.result?.let {
                            // 댓글 데이터를 성공적으로 받아왔을 때 처리
                            callback(it)
                        }
                    } else {
                        Log.e("MyRepository", "댓글을 불러오는 데 실패했습니다.")
                        callback(null)
                    }
                }

                override fun onFailure(call: Call<CommentResponse>, t: Throwable) {
                    Log.e("MyRepository", "네트워크 오류 발생: ${t.message}")
                    callback(null)
                }
            })
        }

        /*
        // 프로필 수정
        fun updateProfile(userTag: String, userPassword: String, userNickname: String, callback: (Boolean) -> Unit) {
            // 수정할 데이터를 Map으로 생성
            val userInfo = mapOf(
                "userTag" to userTag,
                "userPassword" to userPassword,
                "userNickname" to userNickname
            )

            myService.modifyUser(userInfo).enqueue(object : Callback<UserResponse> {
                override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                    if (response.isSuccessful) {
                        val isSuccess = response.body()?.isSuccess ?: false
                        if (isSuccess) {
                            Log.d("MyRepository", "프로필 수정 성공")
                            callback(true)
                        } else {
                            Log.e("MyRepository", "프로필 수정 실패")
                            callback(false)
                        }
                    } else {
                        Log.e("MyRepository", "프로필 수정 실패: ${response.errorBody()?.string()}")
                        callback(false)
                    }
                }
                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.e("MyRepository", "프로필 수정 요청 실패: ${t.message}")
                    callback(false)
                }
            })
        }
        //기존 유저 정보 받아오기
        fun getUserInfo(userTag: String, callback: (UserInfo?) -> Unit) {
            myService.getUserInfo(userTag).enqueue(object : Callback<UserInfoResponse> {
                override fun onResponse(call: Call<UserInfoResponse>, response: Response<UserInfoResponse>) {
                    if (response.isSuccessful) {
                        callback(response.body()?.userInfo)
                    } else {
                        Log.e("MyRepository", "유저 정보 조회 실패")
                        callback(null)
                    }
                }

                override fun onFailure(call: Call<UserInfoResponse>, t: Throwable) {
                    Log.e("MyRepository", "유저 정보 요청 실패: ${t.message}")
                    callback(null)
                }
            })
        }
         */

        // 나의 여행 스레드 조회
        suspend fun getMyThreads(token: String): List<ThreadData>? {
            return try {
                val response = myService.getMyThreads(token)

                Log.d("API_CALL", "응답 코드: ${response.code()}") // ✅ HTTP 응답 코드 확인
                Log.d("API_CALL", "응답 바디: ${response.errorBody()?.string()}") // ✅ 에러 발생 시 바디 확인

                if (response.isSuccessful) {
                    val body = response.body()
                    Log.d("API_CALL", "응답 파싱된 데이터: $body") // ✅ JSON이 올바르게 변환되었는지 확인
                    body?.result
                } else {
                    Log.e("API_ERROR", "API 요청 실패: ${response.errorBody()?.string()}")
                    null
                }
            } catch (e: Exception) {
                Log.e("API_ERROR", "네트워크 오류: ${e.message}")
                null
            }
        }





        //사용자 정보 호출
        fun getUserInfo(callback: (UserInfo?) -> Unit) {
            myService.getUserInfo().enqueue(object : Callback<UserInfoResponse> {
                override fun onResponse(
                    call: Call<UserInfoResponse>,
                    response: Response<UserInfoResponse>
                ) {
                    if (response.isSuccessful && response.body()?.isSuccess == true) {
                        val userInfo = response.body()?.userInfo
                        Log.d("MyRepository", "사용자 정보 조회 성공: $userInfo")
                        callback(userInfo)
                    } else {
                        Log.e("MyRepository", "사용자 정보 조회 실패: ${response.errorBody()?.string()}")
                        callback(null)
                    }
                }

                override fun onFailure(call: Call<UserInfoResponse>, t: Throwable) {
                    Log.e("MyRepository", "사용자 정보 요청 실패: ${t.message}")
                    callback(null)
                }
            })
        }
    }
}