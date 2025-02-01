package com.example.travelbox.presentation.view.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.travelbox.databinding.FragmentScheduleBinding
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import org.threeten.bp.format.DateTimeFormatter
import java.util.Locale

class ScheduleFragment : Fragment() {

    private lateinit var binding: FragmentScheduleBinding
    private val args: ScheduleFragmentArgs by navArgs()
    private var selectedStartDate: CalendarDay? = null
    private var selectedEndDate: CalendarDay? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScheduleBinding.inflate(inflater, container, false)

        // 날짜 기본값 설정
        val selectedDate = CalendarDay.from(args.year, args.month, args.day)
        binding.scheduleCalendarView.selectedDate = selectedDate
        selectedStartDate = selectedDate

        // 월 표시
        binding.monthText.text = selectedDate.month.toString().uppercase(Locale.getDefault())

        // 뒤로가기 버튼
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        // 기간 선택 리스너
        binding.startDate.setOnClickListener {
            selectedStartDate = binding.scheduleCalendarView.selectedDate
            binding.startDate.text = formatDate(selectedStartDate!!)
        }

        binding.endDate.setOnClickListener {
            selectedEndDate = binding.scheduleCalendarView.selectedDate
            binding.endDate.text = formatDate(selectedEndDate!!)
        }

        // 일정 저장 (체크 버튼)
        binding.checkButton.setOnClickListener {
            Toast.makeText(requireContext(), "일정이 저장되었습니다!", Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }

        return binding.root
    }

    private fun formatDate(date: CalendarDay): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd E", Locale.getDefault())
        return date.date.format(formatter)
    }
}
