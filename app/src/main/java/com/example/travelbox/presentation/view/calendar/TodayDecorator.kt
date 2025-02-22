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

class TodayDecorator(context: Context) : DayViewDecorator {
    private val today = CalendarDay.today()
    private val drawable: Drawable? = ContextCompat.getDrawable(context, R.drawable.today_circle)

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return day == today
    }

    override fun decorate(view: DayViewFacade?) {
        drawable ?: return
        view?.setBackgroundDrawable(drawable) // ✅ 초록색 원 적용
        view?.addSpan(android.text.style.ForegroundColorSpan(Color.WHITE)) // ✅ 흰색 텍스트
        view?.addSpan(android.text.style.StyleSpan(Typeface.BOLD)) // ✅ 볼드 처리
    }
}
