package com.example.travelbox.presentation.view.calendar

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.text.style.LineBackgroundSpan
import com.example.travelbox.data.repository.calendar.CalendarQueryEvent
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import java.text.SimpleDateFormat
import java.util.*

/**
 * ✅ 일정이 있는 날짜에 초록색 밑줄을 추가하는 데코레이터
 */
class CalendarEventsDecorator(events: List<CalendarQueryEvent>) : DayViewDecorator {

    private val eventDays = mutableSetOf<CalendarDay>()

    init {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        for (event in events) {
            val startDate = sdf.parse(event.travelStartDate)
            val endDate = sdf.parse(event.travelEndDate)

            if (startDate != null && endDate != null) {
                val calendar = Calendar.getInstance()
                calendar.time = startDate

                while (!calendar.time.after(endDate)) {
                    val day = CalendarDay.from(
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH) + 1,
                        calendar.get(Calendar.DAY_OF_MONTH)
                    )
                    eventDays.add(day)
                    calendar.add(Calendar.DAY_OF_MONTH, 1) // 다음 날짜로 이동
                }
            }
        }
    }

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return eventDays.contains(day)
    }

    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(ThickUnderlineSpan()) // ✅ 일정이 있는 날짜에 초록색 밑줄 추가
    }

    /**
     * ✅ 밑줄을 두껍게 그리는 커스텀 Span
     */
    private class ThickUnderlineSpan : LineBackgroundSpan {
        override fun drawBackground(
            c: Canvas, p: Paint,
            left: Int, right: Int, top: Int, baseline: Int, bottom: Int,
            text: CharSequence, start: Int, end: Int, lineNumber: Int
        ) {
            val paint = Paint()
            paint.color = Color.parseColor("#00A879")
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 6f // ✅ 좀 더 얇게 조정 (8f → 6f)
            paint.isAntiAlias = true

            val padding = 6f  // ✅ 글자와 밑줄 사이 간격
            val startX = left.toFloat() + 40f  // ✅ 좌측 여백 추가
            val endX = right.toFloat() - 40f  // ✅ 우측 여백 추가
            val y = bottom.toFloat() + padding

            c.drawLine(startX, y, endX, y, paint)
        }
    }
}