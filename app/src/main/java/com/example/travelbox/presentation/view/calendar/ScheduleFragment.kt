package com.example.travelbox.presentation.view.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.travelbox.databinding.FragmentScheduleBinding
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.format.WeekDayFormatter
import org.threeten.bp.DayOfWeek

class ScheduleFragment : Fragment() {

    private lateinit var binding: FragmentScheduleBinding
    private val months = listOf(
        "JAN", "FEB", "MAR", "APR", "MAY", "JUN",
        "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScheduleBinding.inflate(inflater, container, false)

        // ✅ 뒤로가기 버튼 클릭 시 이전 화면으로 돌아가기
        binding.backButton.setOnClickListener {
            requireActivity().finish()
        }

        // ✅ Intent로 전달받은 날짜 가져오기 (없으면 오늘 날짜)
        val selectedDateString = arguments?.getString("selected_date") ?: getTodayDateString()
        val dateParts = selectedDateString.split(".")
        val selectedDate = if (dateParts.size == 3) {
            CalendarDay.from(
                dateParts[0].toInt(), // year
                dateParts[1].toInt(), // month
                dateParts[2].toInt()  // day
            )
        } else {
            CalendarDay.today() // 기본값: 오늘 날짜
        }

        // ✅ 선택된 날짜 설정
        binding.selectedDateTextView.text = selectedDateString

        // ✅ Intent에서 받은 월을 표시
        val monthIndex = selectedDate.month - 1
        binding.monthTitle.text = months[monthIndex]  // 예: JAN, FEB

        // ✅ 캘린더 설정 (선택한 날짜 강조 + 요일 포맷 적용 + 헤더 제거)
        setupCalendarView(selectedDate)

        return binding.root
    }

    private fun setupCalendarView(selectedDate: CalendarDay) {
        val calendarView: MaterialCalendarView = binding.scheduleCalendarView

        // ✅ 요일 변경 (CalendarFragment와 동일하게 설정)
        val customWeekFormatter = WeekDayFormatter { dayOfWeek ->
            when (dayOfWeek) {
                DayOfWeek.SUNDAY -> "S"
                DayOfWeek.MONDAY -> "M"
                DayOfWeek.TUESDAY -> "T"
                DayOfWeek.WEDNESDAY -> "W"
                DayOfWeek.THURSDAY -> "T"
                DayOfWeek.FRIDAY -> "F"
                DayOfWeek.SATURDAY -> "S"
                else -> "?"
            }
        }
        calendarView.setWeekDayFormatter(customWeekFormatter)

        // ✅ 캘린더 설정 (터치 비활성화 + 헤더 숨김)
        calendarView.isClickable = false
        calendarView.isFocusable = false
        calendarView.setOnDateChangedListener(null)
        calendarView.setTitleFormatter { "" } // 헤더 제거
        calendarView.topbarVisible = false
        // ✅ 헤더 숨기기 적용
        calendarView.setTitleFormatter { "" }
        calendarView.topbarVisible = false

        // ✅ 선택한 날짜를 `SelectedDateDecorator`로 꾸미기
        calendarView.addDecorator(SelectedDateDecorator(selectedDate))
    }

    private fun getTodayDateString(): String {
        val today = CalendarDay.today()
        return "${today.year}.${today.month}.${today.day}"
    }
}
