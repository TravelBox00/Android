package com.example.travelbox.presentation.view.calendar

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.example.travelbox.R
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade

class SelectedDateDecorator(context: Context, private val selectedDate: CalendarDay) : DayViewDecorator {
    private val drawable: Drawable? = ContextCompat.getDrawable(context, R.drawable.selected_circle)

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return day == selectedDate
    }

    override fun decorate(view: DayViewFacade?) {
        drawable ?: return  // ✅ drawable이 null이면 함수 종료
        view?.setBackgroundDrawable(drawable) // ✅ 회색 원 적용
        view?.addSpan(android.text.style.ForegroundColorSpan(Color.BLACK)) // ✅ 검은색 텍스트
        view?.addSpan(android.text.style.StyleSpan(Typeface.BOLD)) // ✅ 볼드 처리
    }
}
