package com.example.travelbox.presentation.view.calendar

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
 * ✅ 캘린더에서 일정이 있는 날에 초록색 밑줄을 추가하는 데코레이터
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
        view?.addSpan(ThickUnderlineSpan()) // ✅ 초록색 밑줄 추가
    }

    /**
     * ✅ 초록색 밑줄을 두껍게 그리는 커스텀 Span 클래스
     */
    private class ThickUnderlineSpan : LineBackgroundSpan {
        override fun drawBackground(
            c: android.graphics.Canvas, p: Paint,
            left: Int, right: Int, top: Int, baseline: Int, bottom: Int,
            text: CharSequence, start: Int, end: Int, lineNumber: Int
        ) {
            val paint = Paint()
            paint.color = Color.parseColor("#00A879") // ✅ 초록색
            paint.style = Paint.Style.FILL
            paint.strokeWidth = 8f // ✅ 밑줄 두께 조정

            val rect = RectF(left.toFloat(), bottom.toFloat() + 8f, right.toFloat(), bottom.toFloat() + 16f)
            c.drawRect(rect, paint)
        }
    }
}
