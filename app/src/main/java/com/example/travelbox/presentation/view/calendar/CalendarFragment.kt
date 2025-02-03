package com.example.travelbox.presentation.view.calendar

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import com.example.travelbox.R
import com.example.travelbox.databinding.FragmentCalendarBinding
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import com.prolificinteractive.materialcalendarview.format.WeekDayFormatter
import com.jakewharton.threetenabp.AndroidThreeTen
import org.threeten.bp.DayOfWeek
import java.util.Locale
import androidx.navigation.fragment.findNavController
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan

class CalendarFragment : Fragment() {

    private lateinit var binding: FragmentCalendarBinding
    private var selectedDate: CalendarDay? = null

    private val months = listOf(
        "JAN", "FEB", "MAR", "APR", "MAY", "JUN",
        "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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

        // 현재 날짜에 맞는 초기 월 설정
        val today = CalendarDay.today()
        val currentMonthIndex = today.month - 1
        updateMonthTabs(currentMonthIndex)
        scrollToMonth(currentMonthIndex)
        binding.calendarView.currentDate = today
        binding.calendarView.selectedDate = today

        // ✅ 오늘 날짜 및 선택한 날짜 데코레이터 적용
        binding.calendarView.addDecorator(TodayDecorator())

        // 월 탭 클릭 리스너 설정
        binding.monthTabs.children.forEachIndexed { index, view ->
            (view as TextView).setOnClickListener {
                updateMonthTabs(index)
                scrollToMonth(index)
                binding.calendarView.currentDate = CalendarDay.from(today.year, index + 1, 1)
            }
        }

        binding.calendarView.setOnDateChangedListener(OnDateSelectedListener { _, date, _ ->
            // ✅ 기존 선택된 날짜 스타일 제거 (오늘 날짜 데코레이터 유지)
            binding.calendarView.removeDecorators()

            // ✅ 오늘 날짜 데코레이터는 항상 유지
            binding.calendarView.addDecorator(TodayDecorator())

            // ✅ 새로운 선택된 날짜에 회색 원 적용
            selectedDate = date
            binding.calendarView.addDecorator(SelectedDateDecorator(date))

            // ✅ 즉시 반영
            binding.calendarView.invalidateDecorators()

            Toast.makeText(
                requireContext(),
                "선택한 날짜: ${date.year}-${date.month}-${date.day}",
                Toast.LENGTH_SHORT
            ).show()
        })



        if (savedInstanceState == null) {
            childFragmentManager.beginTransaction()
                .replace(R.id.fab_menu_container, FabMenuFragment())
                .commit()
        }
        binding.calendarView.setOnDateChangedListener(OnDateSelectedListener { _, date, _ ->
            selectedDate = date
            binding.calendarView.invalidateDecorators()

            Toast.makeText(
                requireContext(),
                "선택한 날짜: ${date.year}-${date.month}-${date.day}",
                Toast.LENGTH_SHORT
            ).show()
        })

        if (savedInstanceState == null) {

            childFragmentManager.beginTransaction()
                .replace(R.id.fab_menu_container, FabMenuFragment())
                .commit()
        }

        return binding.root
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
     * 선택된 월이 HorizontalScrollView의 중앙에 오도록 스크롤합니다.
     */
    private fun scrollToMonth(monthIndex: Int) {
        val monthView = binding.monthTabs.getChildAt(monthIndex)
        val scrollToX = monthView.left - (binding.horizontalScrollView.width - monthView.width) / 2
        binding.horizontalScrollView.smoothScrollTo(scrollToX, 0)
    }
}
