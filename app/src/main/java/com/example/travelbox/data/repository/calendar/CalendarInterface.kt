package com.example.travelbox.data.repository.calendar

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
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

    // 일정 삭제 API
    @DELETE("/calendar/remove")
    fun deleteCalendarEvent(
        @Body request: CalendarEventDeleteRequest
    ): Call<CalendarEventResponse>

    // ✅ 일정 조회 API
    @GET("/calendar/my")
    fun getUserCalendarEvents(
        @Query("userTag") userTag: String,
        @Query("date") date: String
    ): Call<CalendarQueryResponse>
}
