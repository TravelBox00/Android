package com.example.travelbox.presentation.view.calendar

import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.travelbox.databinding.FragmentCalendarBinding
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener

class CalendarFragment : Fragment() {
    private lateinit var binding: FragmentCalendarBinding
    private var selectedDate: CalendarDay? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalendarBinding.inflate(inflater, container, false)

        // 오늘 날짜 데코레이터 추가
        binding.calendarView.addDecorator(TodayDecorator())

        // 날짜 선택 리스너 설정
        binding.calendarView.setOnDateChangedListener(OnDateSelectedListener { widget, date, selected ->
            selectedDate = date
            binding.calendarView.invalidateDecorators() // 데코레이터 갱신
            binding.calendarView.addDecorator(SelectedDateDecorator(date))

            val selectedDateString = "${date.year}-${date.month + 1}-${date.day}"
            Toast.makeText(requireContext(), "선택한 날짜: $selectedDateString", Toast.LENGTH_SHORT).show()
        })

        // 월 변경 이벤트 처리
        binding.calendarView.setOnMonthChangedListener { widget, date ->
            updateMonthTabs(date.month)
        }

        // 플로팅 버튼 클릭 리스너
        binding.addEventButton.setOnClickListener {
            Toast.makeText(requireContext(), "이벤트 추가 버튼 클릭", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    // 현재 월에 맞게 탭 스타일 업데이트
    private fun updateMonthTabs(currentMonth: Int) {
        val months = listOf("JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC")

        // 자식 뷰를 순회하며 업데이트
        for (i in 0 until binding.monthTabs.childCount) {
            val textView = binding.monthTabs.getChildAt(i) as TextView
            if (i == currentMonth - 1) {
                textView.setTextColor(Color.parseColor("#007151")) // 초록색
            } else {
                textView.setTextColor(Color.parseColor("#61646B")) // 회색
            }
        }
    }
}
