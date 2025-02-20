package com.example.travelbox.data.repository.calendar

data class CalendarQueryResponse(
    val isSuccess: Boolean,
    val result: List<CalendarQueryEvent>?
)

data class CalendarQueryEvent(
    val travelId: Int,
    val travelTitle: String,
    val travelContent: String,
    val travelStartDate: String,
    val travelEndDate: String
)
