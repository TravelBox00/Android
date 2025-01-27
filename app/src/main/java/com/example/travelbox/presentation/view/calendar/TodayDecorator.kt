package com.example.travelbox.presentation.view.calendar

import android.graphics.Color
import android.text.style.ForegroundColorSpan
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade

class TodayDecorator : DayViewDecorator {
    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return day == CalendarDay.today() // 오늘 날짜인지 확인
    }

    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(ForegroundColorSpan(Color.parseColor("#00A879"))) // 초록색
    }
}
