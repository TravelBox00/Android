package com.example.travelbox.data.repository.calendar

import android.util.Log
import com.example.travelbox.data.network.ApiNetwork
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CalendarRepository {

    companion object {
        private val service = ApiNetwork.createService(CalendarInterface::class.java)

        // ✅ 일정 추가 API
        fun addCalendarEvent(
            userId: Int,
            title: String,
            content: String,
            startDate: String,
            endDate: String,
            callback: (Boolean, String?) -> Unit
        ) {
            val request = CalendarEventRequest(userId, null, title, content, startDate, endDate)

            service.addCalendarEvent(request).enqueue(object : Callback<CalendarEventResponse> {
                override fun onResponse(
                    call: Call<CalendarEventResponse>,
                    response: Response<CalendarEventResponse>
                ) {
                    callback(response.isSuccessful, response.body()?.message)
                }

                override fun onFailure(call: Call<CalendarEventResponse>, t: Throwable) {
                    callback(false, "네트워크 오류")
                }
            })
        }

        // ✅ 일정 수정 API
        fun updateCalendarEvent(
            userId: Int,
            travelId: Int,
            title: String,
            content: String,
            startDate: String,
            endDate: String,
            callback: (Boolean, String?) -> Unit
        ) {
            val request = CalendarEventRequest(userId, travelId, title, content, startDate, endDate)

            service.updateCalendarEvent(request).enqueue(object : Callback<CalendarEventResponse> {
                override fun onResponse(
                    call: Call<CalendarEventResponse>,
                    response: Response<CalendarEventResponse>
                ) {
                    callback(response.isSuccessful, response.body()?.message)
                }

                override fun onFailure(call: Call<CalendarEventResponse>, t: Throwable) {
                    callback(false, "네트워크 오류")
                }
            })
        }

        // ✅ 일정 삭제 API
        fun deleteCalendarEvent(
            travelId: Int,
            callback: (Boolean, String?) -> Unit
        ) {
            val request = CalendarEventDeleteRequest(travelId)

            service.deleteCalendarEvent(request).enqueue(object : Callback<CalendarEventResponse> {
                override fun onResponse(
                    call: Call<CalendarEventResponse>,
                    response: Response<CalendarEventResponse>
                ) {
                    callback(response.isSuccessful, response.body()?.message)
                }

                override fun onFailure(call: Call<CalendarEventResponse>, t: Throwable) {
                    callback(false, "네트워크 오류")
                }
            })
        }
    }
}
