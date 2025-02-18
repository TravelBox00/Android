package com.example.travelbox.data.repository.calendar

data class CalendarEventResponse(
    val isSuccess: Boolean,
    val message: String,
    val result: CalendarEventResult?
)

data class CalendarEventResult(
    val travelId: Int // 추가, 수정 시 반환되는 일정 ID
)
