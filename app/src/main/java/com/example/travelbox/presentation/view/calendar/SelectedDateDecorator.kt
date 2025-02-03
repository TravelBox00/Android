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
        return day == selectedDate // 선택한 날짜에만 적용
    }

    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(ForegroundColorSpan(Color.BLACK)) // 검은색 글씨
        view?.addSpan(StyleSpan(Typeface.BOLD)) // 볼드 적용
        view?.setBackgroundDrawable(createCircleDrawable("#DADADA")) // ✅ 회색 원 적용
    }

    private fun createCircleDrawable(color: String): GradientDrawable {
        return GradientDrawable().apply {
            shape = GradientDrawable.OVAL
            setColor(Color.parseColor(color))
            setSize(40, 40) // ✅ 기존보다 원 크기 살짝 키움
        }
    }
}
