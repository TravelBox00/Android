package com.example.travelbox.presentation.view.calendar

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.InsetDrawable
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade

class TodayDecorator : DayViewDecorator {
    private val today = CalendarDay.today()

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return day == today
    }

    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(ForegroundColorSpan(Color.WHITE)) // 오늘 날짜 흰색 글씨
        view?.addSpan(StyleSpan(Typeface.BOLD)) // 볼드 처리
        view?.setBackgroundDrawable(createCircleDrawable("#008F63")) // ✅ 오늘 날짜는 초록색 원
    }

    private fun createCircleDrawable(color: String): GradientDrawable {
        return GradientDrawable().apply {
            shape = GradientDrawable.OVAL
            setColor(Color.parseColor(color))
            setSize(30, 30) // ✅ 원 크기 조정
        }
    }
}
