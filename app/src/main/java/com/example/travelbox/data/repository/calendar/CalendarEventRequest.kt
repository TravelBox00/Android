package com.example.travelbox.data.repository.calendar

data class CalendarEventRequest(
    val userId: Int, // 로그인한 사용자 ID
    val travelId: Int?, // 수정, 삭제 시 필요
    val travelTitle: String,
    val travelContent: String,
    val travelStartDate: String, // "YYYY-MM-DD" 형식
    val travelEndDate: String    // "YYYY-MM-DD" 형식
)
