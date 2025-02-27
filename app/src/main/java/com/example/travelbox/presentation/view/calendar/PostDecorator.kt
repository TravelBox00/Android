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

class PostDecorator(
    private val context: Context,
    private val postDates: List<CalendarDay>
) : DayViewDecorator {

    private val drawable: Drawable? = ContextCompat.getDrawable(context, R.drawable.post_circle) // ✅ 파란색 테두리 원

    override fun shouldDecorate(day: CalendarDay): Boolean {
        return postDates.contains(day)  // ✅ 해당 날짜가 게시물 있는 날인지 확인
    }

    override fun decorate(view: DayViewFacade) {
        drawable ?: return
        view.setBackgroundDrawable(drawable)
        view.addSpan(android.text.style.StyleSpan(Typeface.BOLD)) // ✅ 볼드 텍스트
    }
}
