package com.example.travelbox.presentation.view.calendar

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade

class SelectedDateDecorator(private val selectedDate: CalendarDay) : DayViewDecorator {
    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return day == selectedDate // 선택된 날짜인지 확인
    }

    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(ForegroundColorSpan(Color.BLACK)) // 텍스트 색상 유지 (검은색)
        view?.addSpan(StyleSpan(Typeface.BOLD)) // 볼드 유지
        view?.setBackgroundDrawable(createSmallCircleDrawable("#DADADA")) // ✅ 작은 회색 원 배경 적용
    }

    private fun createSmallCircleDrawable(color: String): GradientDrawable {
        return GradientDrawable().apply {
            shape = GradientDrawable.OVAL
            setColor(Color.parseColor(color))
            setSize(30, 30) // ✅ 바깥 원이 커지지 않도록 작은 원으로 설정
        }
    }
}
