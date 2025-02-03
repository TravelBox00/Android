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
    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return day == CalendarDay.today() // 오늘 날짜인지 확인
    }

    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(ForegroundColorSpan(Color.WHITE)) // 텍스트 색상: 흰색
        view?.addSpan(StyleSpan(Typeface.BOLD)) // 볼드 유지
        view?.setBackgroundDrawable(createSmallCircleDrawable("#00A879")) // ✅ 작은 초록색 원 적용
    }

    private fun createSmallCircleDrawable(color: String): GradientDrawable {
        return GradientDrawable().apply {
            shape = GradientDrawable.OVAL
            setColor(Color.parseColor(color))
            setSize(30, 30) // ✅ 크기 줄이기
        }
    }
}