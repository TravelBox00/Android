package com.example.travelbox.data.repository.auth

import android.util.Log
import com.example.travelbox.data.network.ApiNetwork
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthRepository {

    companion object {

        private val service = ApiNetwork.createService(AuthInterface::class.java)


        // 로그인
        fun login(userTag: String, userPassword: String, callback: (Boolean) -> Unit) {
            val request = LoginRequest(userTag, userPassword)

            service.login(request).enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.isSuccessful) {
                        val result = response.body()?.result
                        if (result != null) {
                            // AccessToken, RefreshToken 저장
                            ApiNetwork.saveAccessToken(result.accessToken)
                            ApiNetwork.saveRefreshToken(result.refreshToken)
                            //  로그인 성공 시 userTag 저장
                            ApiNetwork.saveUserTag(userTag)
                            Log.d("LoginRepository", "로그인 성공: ${result.accessToken}")
                            callback(true)
                        } else {
                            Log.e("LoginRepository", "로그인 응답 : null")
                            callback(false)
                        }
                    } else {
                        Log.e("LoginRepository", "로그인 실패: ${response.errorBody()?.string()}")
                        callback(false)
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.e("LoginRepository", "로그인 요청 실패: ${t.message}")
                    callback(false)
                }
            })
        }

        // Access Token 재발급
        fun refreshAccessToken(userTag: String, callback: (Boolean) -> Unit) {
            val refreshToken = ApiNetwork.getRefreshToken()

            if (refreshToken.isNullOrEmpty()) {
                Log.e("AuthRepository", "토큰 없음. 재발급 불가")
                callback(false)
                return
            }

            val request = RefreshTokenRequest(userTag, refreshToken)

            service.refreshAccessToken(request).enqueue(object : Callback<RefreshTokenResponse> {
                override fun onResponse(call: Call<RefreshTokenResponse>, response: Response<RefreshTokenResponse>) {
                    if (response.isSuccessful) {
                        val result = response.body()?.result
                        if (result != null) {
                            Log.d("AuthRepository", "Access Token 재발급 성공")

                            // 새 토큰 저장
                            ApiNetwork.saveAccessToken(result.accessToken)
                            ApiNetwork.saveRefreshToken(result.refreshToken)

                            callback(true)
                        } else {
                            Log.e("AuthRepository", "Access Token 재발급 응답이 비어 있음")
                            callback(false)
                        }
                    } else {
                        Log.e("AuthRepository", "Access Token 재발급 실패: ${response.errorBody()?.string()}")
                        callback(false)
                    }
                }

                override fun onFailure(call: Call<RefreshTokenResponse>, t: Throwable) {
                    Log.e("AuthRepository", "Access Token 재발급 요청 실패: ${t.message}")
                    callback(false)
                }
            })
        }

        // 로그아웃
        fun logout(callback: (Boolean) -> Unit) {
            service.logout().enqueue(object : Callback<LogoutResponse> {
                override fun onResponse(call: Call<LogoutResponse>, response: Response<LogoutResponse>) {
                    if (response.isSuccessful) {
                        val result = response.body()
                        if (result?.isSuccess == true) {
                            Log.d("AuthRepository", "로그아웃 성공")

                            // 저장된 토큰 삭제
                            ApiNetwork.saveAccessToken("")
                            ApiNetwork.saveRefreshToken("")

                            callback(true)
                        } else {
                            Log.e("AuthRepository", "로그아웃 실패: 응답값 없음")
                            callback(false)
                        }
                    } else {
                        Log.e("AuthRepository", "로그아웃 실패: ${response.errorBody()?.string()}")
                        callback(false)
                    }
                }

                override fun onFailure(call: Call<LogoutResponse>, t: Throwable) {
                    Log.e("AuthRepository", "로그아웃 요청 실패: ${t.message}")
                    callback(false)
                }
            })
        }

        // 회원가입
        fun signUp(userTag: String, userPassword: String, userNickname: String, callback: (Boolean) -> Unit) {
            val request = SignUpRequest(userTag, userPassword, userNickname)

            service.signUp(request).enqueue(object : Callback<SignUpResponse> {
                override fun onResponse(
                    call: Call<SignUpResponse>,
                    response: Response<SignUpResponse>
                ) {
                    if (response.isSuccessful) {
                        val result = response.body()?.result
                        if (result != null) {
                            Log.d("AuthRepository", "회원가입 성공: ${result.userTag}")
                            callback(true)
                        } else {
                            Log.e("AuthRepository", "회원가입 응답 : null")
                            callback(false)
                        }
                    } else {
                        Log.d("AuthRepository", "회원가입 요청 데이터: $userTag, $userPassword, $userNickname")
                        Log.e("AuthRepository", "회원가입 실패: ${response.errorBody()?.string()}")
                        callback(false)
                    }
                }

                override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                    Log.e("AuthRepository", "회원가입 요청 실패: ${t.message}")
                    callback(false)
                }
            })
        }

        //ID 중복확인
        fun duplicate(userTag: String, callback: (Boolean) -> Unit) {
            service.duplicate(userTag).enqueue(object : Callback<DuplicateResponse> {
                override fun onResponse(call: Call<DuplicateResponse>, response: Response<DuplicateResponse>) {
                    if (response.isSuccessful) {
                        val result = response.body()?.result
                        Log.d("AuthRepository", "아이디 중복 확인 성공: $result")
                        callback(result ?: false)  // Boolean 값 그대로 사용
                    } else {
                        Log.e("AuthRepository", "아이디 중복 확인 실패: ${response.errorBody()?.string()}")
                        callback(false)
                    }
                }

                override fun onFailure(call: Call<DuplicateResponse>, t: Throwable) {
                    Log.e("AuthRepository", "아이디 중복 확인 요청 실패: ${t.message}")
                    callback(false)
                }
            })
        }

    }
}

