package com.example.travelbox.data.network


import android.app.VoiceInteractor
import android.content.Context
import android.content.SharedPreferences
import com.example.travelbox.BuildConfig
import com.example.travelbox.TravelBoxApplication
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiNetwork{

    private lateinit var prefs: SharedPreferences

    // SharedPreferences 초기화
    fun init(context: Context) {
        prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    }

    // Access Token 저장
    fun saveAccessToken(token: String) {
        if (::prefs.isInitialized) {  // 초기화 여부 체크
            prefs.edit().putString("access_token", token).apply()
        }
    }

    // Access Token 가져오기
    fun getAccessToken(): String? {
        return if (::prefs.isInitialized) prefs.getString("access_token", null) else null
    }

    // OkHttpClient 설정 (JWT 토큰 자동 추가)
    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor { chain: Interceptor.Chain ->
                val requestBuilder: Request.Builder = chain.request().newBuilder()
                getAccessToken()?.let {
                    requestBuilder.addHeader("Authorization", "Bearer $it")
                }
                chain.proceed(requestBuilder.build())
            }
            .build()
    }

    // Retrofit 객체 생성
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL) // 환경별 Base URL 자동 적용
            .addConverterFactory(GsonConverterFactory.create()) // JSON 변환기 추가
            .client(client) // OkHttp 클라이언트 추가
            .build()
    }

    fun <T> createService(service: Class<T>): T {
        return retrofit.create(service)
    }

    // RefreshToken 저장 기능
    fun saveRefreshToken(token: String) {
        if (::prefs.isInitialized) {
            prefs.edit().putString("refresh_token", token).apply()
        }
    }

    fun getRefreshToken(): String? {
        return if (::prefs.isInitialized) prefs.getString("refresh_token", null) else null
    }
    // userTag 저장
    fun saveUserTag(userTag: String) {
        if (::prefs.isInitialized) {
            prefs.edit().putString("user_tag", userTag).apply()
        }
    }

    // userTag 불러오기
    fun getUserTag(): String? {
        return if (::prefs.isInitialized) prefs.getString("user_tag", null) else null
    }
}