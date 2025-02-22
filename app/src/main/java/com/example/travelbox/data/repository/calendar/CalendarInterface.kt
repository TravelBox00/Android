package com.example.travelbox.data.repository.calendar

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface CalendarInterface {

    // 일정 추가 API
    @POST("/calendar/add")
    fun addCalendarEvent(
        @Body request: CalendarEventRequest
    ): Call<CalendarEventResponse>

    // 일정 수정 API
    @PUT("/calendar/fix")
    fun updateCalendarEvent(
        @Body request: CalendarEventRequest
    ): Call<CalendarEventResponse>

    @HTTP(method = "DELETE", path = "/calendar/remove", hasBody = true)
    fun deleteCalendarEvent(
        @Header("Authorization") token: String,
        @Body request: CalendarEventDeleteRequest // ✅ travelId를 JSON Body로 전달
    ): Call<CalendarEventResponse>


    // ✅ 일정 조회 API (Authorization 헤더 추가)
    @GET("/calendar/my")
    fun getUserCalendarEvents(
        @Header("Authorization") token: String,  // ✅ Bearer 토큰 추가
        @Query("userTag") userTag: String,
        @Query("date") date: String
    ): Call<CalendarQueryResponse>
}