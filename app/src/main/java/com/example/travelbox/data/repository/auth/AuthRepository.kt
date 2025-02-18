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
    }
}