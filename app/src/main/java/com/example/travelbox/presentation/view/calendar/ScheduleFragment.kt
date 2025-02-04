package com.example.travelbox.presentation.view.calendar

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.travelbox.databinding.FragmentScheduleBinding
import com.example.travelbox.presentation.view.calendar.decorators.RangeDecorator
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener
import com.prolificinteractive.materialcalendarview.format.WeekDayFormatter
import java.util.Calendar
import org.threeten.bp.DayOfWeek

class ScheduleFragment : Fragment() {

    private lateinit var binding: FragmentScheduleBinding
    private val months = listOf("JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC")

    private var startDate: CalendarDay? = null
    private var endDate: CalendarDay? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScheduleBinding.inflate(inflater, container, false)

        // ✅ 뒤로가기 버튼
        binding.backButton.setOnClickListener {
            requireActivity().finish()
        }

        // ✅ Intent에서 전달받은 날짜 가져오기
        val selectedDateString = arguments?.getString("selected_date") ?: getTodayDateString()
        val dateParts = selectedDateString.split(".")
        val selectedDate = if (dateParts.size == 3) {
            CalendarDay.from(dateParts[0].toInt(), dateParts[1].toInt(), dateParts[2].toInt())
        } else {
            CalendarDay.today()
        }

        // ✅ 선택한 날짜에 따라 월 타이틀 업데이트
        updateMonthTitle(selectedDate.month)

        // ✅ 기본적으로 캘린더에서 선택한 날짜를 출발일로 설정
        startDate = selectedDate
        endDate = selectedDate
        updateSelectedDateUI()

        // ✅ 캘린더 설정 및 선택한 날짜 즉시 반영
        setupCalendarView()
        updateCalendarSelection(selectedDate)

        // ✅ 출발일 선택
        binding.startDatePicker.setOnClickListener {
            showDatePicker(true)
        }

        // ✅ 종료일 선택
        binding.endDatePicker.setOnClickListener {
            showDatePicker(false)
        }

        // ✅ 캘린더 스와이프 시 월 제목 변경
        binding.scheduleCalendarView.setOnMonthChangedListener(OnMonthChangedListener { _, date ->
            updateMonthTitle(date.month)
        })

        return binding.root
    }

    /**
     * ✅ 월 타이틀 업데이트 함수
     */
    private fun updateMonthTitle(month: Int) {
        binding.monthTitle.text = months[month - 1]
    }

    /**
     * ✅ 날짜 선택 기능 추가 (출발일 or 종료일)
     */
    private fun showDatePicker(isStartDate: Boolean) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
            val newDate = CalendarDay.from(selectedYear, selectedMonth + 1, selectedDay)

            if (isStartDate) {
                startDate = newDate
                if (endDate != null && startDate!!.isAfter(endDate!!)) {
                    Toast.makeText(requireContext(), "출발일은 종료일보다 앞서야 합니다.", Toast.LENGTH_SHORT).show()
                    startDate = null
                }
            } else {
                endDate = newDate
                if (startDate != null && endDate!!.isBefore(startDate!!)) {
                    Toast.makeText(requireContext(), "종료일은 출발일보다 이후여야 합니다.", Toast.LENGTH_SHORT).show()
                    endDate = null
                }
            }

            updateSelectedDateUI()
            updateCalendarSelection(startDate)
            updateMonthTitle(newDate.month) // ✅ 선택된 날짜 기준으로 월 타이틀 업데이트
        }, year, month, day).show()
    }

    /**
     * ✅ 선택한 날짜 UI 업데이트
     */
    private fun updateSelectedDateUI() {
        binding.startDatePicker.text = startDate?.let { "${it.year}.${it.month}.${it.day}" } ?: "출발일 선택"
        binding.endDatePicker.text = endDate?.let { "${it.year}.${it.month}.${it.day}" } ?: "종료일 선택"

        if (startDate != null && endDate != null) {
            updateCalendarRange()
        }
    }

    /**
     * ✅ 캘린더에서 선택한 날짜 즉시 반영
     */
    private fun updateCalendarSelection(selectedDate: CalendarDay?) {
        if (selectedDate != null) {
            binding.scheduleCalendarView.selectedDate = selectedDate
            binding.scheduleCalendarView.currentDate = selectedDate
            binding.scheduleCalendarView.invalidateDecorators()
        }
    }

    /**
     * ✅ 캘린더에 범위 선택 적용
     */
    private fun updateCalendarRange() {
        val calendarView: MaterialCalendarView = binding.scheduleCalendarView
        calendarView.removeDecorators() // 기존 데코레이터 제거

        // ✅ 출발일과 종료일 범위 적용
        if (startDate != null && endDate != null) {
            calendarView.addDecorator(RangeDecorator(startDate!!, endDate!!))
        }
        // ✅ 캘린더 설정 (터치 비활성화 + 헤더 숨김)
        calendarView.setOnDateChangedListener(null)
        calendarView.setTitleFormatter { "" } // 헤더 제거
        calendarView.topbarVisible = false
        calendarView.invalidateDecorators()
    }

    /**
     * ✅ 캘린더의 요일 포맷 설정 (S, M, T, W, T, F, S)
     */
    private fun setupCalendarView() {
        val calendarView: MaterialCalendarView = binding.scheduleCalendarView

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
    }

    private fun getTodayDateString(): String {
        val today = Calendar.getInstance()
        return "${today.get(Calendar.YEAR)}.${today.get(Calendar.MONTH) + 1}.${today.get(Calendar.DAY_OF_MONTH)}"
    }
}
