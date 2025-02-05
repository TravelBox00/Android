package com.example.travelbox.presentation.view.calendar.decorators

import android.graphics.Color
import android.graphics.Paint
import android.text.style.ForegroundColorSpan
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.spans.DotSpan

class RangeDecorator(private val startDate: CalendarDay, private val endDate: CalendarDay) : DayViewDecorator {

    private val paint = Paint().apply {
        color = Color.LTGRAY // 회색 원 색상
        style = Paint.Style.FILL
    }

    override fun shouldDecorate(day: CalendarDay): Boolean {
        return (day == startDate || day == endDate || (day.isAfter(startDate) && day.isBefore(endDate)))
    }

    override fun decorate(view: DayViewFacade) {
        view.addSpan(DotSpan(10f, Color.LTGRAY)) // 날짜 아래 회색 점 추가
        view.addSpan(ForegroundColorSpan(Color.DKGRAY)) // 텍스트 색상 변경 (더 어두운 회색)
    }
}
