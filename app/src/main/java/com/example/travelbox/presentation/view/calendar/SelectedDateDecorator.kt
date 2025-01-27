package com.example.travelbox.presentation.view.calendar

import android.graphics.Color
import android.text.style.ForegroundColorSpan
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade

class SelectedDateDecorator(private val selectedDate: CalendarDay) : DayViewDecorator {
    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return day == selectedDate // 선택된 날짜인지 확인
    }

    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(ForegroundColorSpan(Color.parseColor("#DADADA"))) // 회색
    }
}
