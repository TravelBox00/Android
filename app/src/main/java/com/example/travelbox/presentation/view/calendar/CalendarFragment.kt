package com.example.travelbox.presentation.view.calendar

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.children
import androidx.fragment.app.Fragment
import com.example.travelbox.R
import com.example.travelbox.databinding.FragmentCalendarBinding
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener
import com.prolificinteractive.materialcalendarview.format.WeekDayFormatter
import com.jakewharton.threetenabp.AndroidThreeTen
import org.threeten.bp.DayOfWeek

class CalendarFragment : Fragment() {

    private lateinit var binding: FragmentCalendarBinding
    private var selectedDate: CalendarDay = CalendarDay.today()  // ✅ 기본값: 오늘 날짜

    private val months = listOf(
        "JAN", "FEB", "MAR", "APR", "MAY", "JUN",
        "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"
    )

    override fun onCreateView(
        inflater: android.view.LayoutInflater, container: android.view.ViewGroup?,
        savedInstanceState: Bundle?
    ): android.view.View {
        binding = FragmentCalendarBinding.inflate(inflater, container, false)

        AndroidThreeTen.init(requireContext())

        // ✅ 요일 변경: '일' → 'S', '월' → 'M' 등으로 변경
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
        binding.calendarView.setWeekDayFormatter(customWeekFormatter)

        // ✅ 헤더 숨기기 적용
        binding.calendarView.setTitleFormatter { "" }
        binding.calendarView.topbarVisible = false

        // ✅ 현재 날짜에 맞게 연도와 월 초기화
        val today = CalendarDay.today()
        updateYearAndMonth(today.year, today.month)

        // ✅ 캘린더에서 월이 변경될 때 연도와 월 업데이트
        binding.calendarView.setOnMonthChangedListener(OnMonthChangedListener { _, date ->
            updateYearAndMonth(date.year, date.month)
        })

        // ✅ 기존 기능 유지: 월 탭 클릭하면 해당 월로 이동
        binding.monthTabs.children.forEachIndexed { index, view ->
            (view as TextView).setOnClickListener {
                updateMonthTabs(index)
                scrollToMonth(index)
                binding.calendarView.currentDate = CalendarDay.from(today.year, index + 1, 1)
            }
        }

        // ✅ 날짜 선택 이벤트
        binding.calendarView.setOnDateChangedListener(OnDateSelectedListener { _, date, _ ->
            selectedDate = date  // ✅ 선택한 날짜 저장
            binding.calendarView.invalidateDecorators()
            Toast.makeText(requireContext(), "선택한 날짜: ${date.year}.${date.month}.${date.day}", Toast.LENGTH_SHORT).show()
        })

        // ✅ Fab 버튼 클릭 시 스케줄 이동 설정
        if (savedInstanceState == null) {
            childFragmentManager.beginTransaction()
                .replace(R.id.fab_menu_container, FabMenuFragment { openScheduleActivity() })  // ✅ Fab 메뉴에서 스케줄 버튼 클릭 시 실행
                .commit()
        }

        return binding.root
    }

    /**
     * ✅ Fab 버튼에서 스케줄 버튼 클릭 시 실행되는 함수
     * 선택한 날짜를 Intent로 `ScheduleActivity`에 전달
     */
    private fun openScheduleActivity() {
        val intent = Intent(requireContext(), ScheduleActivity::class.java).apply {
            putExtra("selected_date", "${selectedDate.year}.${selectedDate.month}.${selectedDate.day}")
        }
        startActivity(intent)
    }
    private fun updateMonthTabs(selectedMonth: Int) {
        binding.monthTabs.children.forEachIndexed { index, view ->
            val monthTextView = view as TextView
            val monthText = months[index]  // JAN, FEB, ...

            if (index == selectedMonth) {
                // ✅ 선택된 월 (검은색 + 굵은 글씨 + #00A879 밑줄)
                val spannableString = SpannableString(monthText).apply {
                    setSpan(ForegroundColorSpan(Color.BLACK), 0, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                    setSpan(UnderlineSpan(), 0, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE) // 기본 밑줄
                }

                monthTextView.text = spannableString
                monthTextView.setTypeface(null, Typeface.BOLD)
                monthTextView.textSize = 18f
            } else {
                // ✅ 기본 월 스타일 (회색, 일반 글씨)
                monthTextView.text = monthText
                monthTextView.setTextColor(Color.parseColor("#61646B"))
                monthTextView.setTypeface(null, Typeface.NORMAL)
                monthTextView.textSize = 14f
            }
        }
    }
    /**
     * ✅ 연도 및 월을 업데이트하는 함수
     */
    private fun updateYearAndMonth(year: Int, month: Int) {
        binding.yearText.text = year.toString()

        binding.monthTabs.children.forEachIndexed { index, view ->
            val monthTextView = view as TextView
            monthTextView.text = months[index]

            if (index == month - 1) {
                // ✅ 선택된 월 (검은색 + 굵은 글씨 + 초록색 밑줄)
                val spannableString = SpannableString(monthTextView.text).apply {
                    setSpan(ForegroundColorSpan(Color.BLACK), 0, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                    setSpan(UnderlineSpan(), 0, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
                monthTextView.text = spannableString
                monthTextView.setTypeface(null, Typeface.BOLD)
                monthTextView.textSize = 18f
            } else {
                // ✅ 기본 월 스타일 (회색, 일반 글씨)
                monthTextView.text = months[index]
                monthTextView.setTextColor(Color.parseColor("#61646B"))
                monthTextView.setTypeface(null, Typeface.NORMAL)
                monthTextView.textSize = 14f
            }
        }

        scrollToMonth(month - 1)
    }

    /**
     * ✅ 선택된 월이 `HorizontalScrollView`의 중앙에 오도록 자동 스크롤
     */
    private fun scrollToMonth(monthIndex: Int) {
        val monthView = binding.monthTabs.getChildAt(monthIndex)
        val scrollToX = monthView.left - (binding.horizontalScrollView.width - monthView.width) / 2
        binding.horizontalScrollView.smoothScrollTo(scrollToX, 0)
    }
}
