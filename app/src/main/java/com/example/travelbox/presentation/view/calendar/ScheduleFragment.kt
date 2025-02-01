//package com.example.travelbox.presentation.view.calendar
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import com.example.travelbox.databinding.FragmentScheduleBinding
//
//class ScheduleFragment : Fragment() {
//
//    private lateinit var binding: FragmentScheduleBinding
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        binding = FragmentScheduleBinding.inflate(inflater, container, false)
//
//        setupHeader()
//        setupUI()
//
//        return binding.root
//    }
//
//    /**
//     * ✅ 상단 헤더 설정 (뒤로가기 버튼 기능 포함)
//     */
//    private fun setupHeader() {
//        binding.backButton.setOnClickListener {
//            requireActivity().onBackPressedDispatcher.onBackPressed()
//        }
//    }
//
//    /**
//     * ✅ 선택한 날짜를 UI에 표시
//     */
//    private fun setupUI() {
//        // `Bundle`에서 전달된 날짜 데이터 가져오기
//        val year = arguments?.getInt("year", 0) ?: 0
//        val month = arguments?.getInt("month", 0) ?: 0
//        val day = arguments?.getInt("day", 0) ?: 0
//
//        // 상단 월 표시
//        val monthNames = listOf(
//            "JAN", "FEB", "MAR", "APR", "MAY", "JUN",
//            "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"
//        )
//        binding.monthTextView.text = monthNames[month - 1] // 월 텍스트 설정
//
//        // 캘린더에서 선택된 날짜 표시
//        binding.calendarView.selectedDate = org.threeten.bp.LocalDate.of(year, month, day)
//
//        // 일정 날짜 설정
//        binding.dateTextView.text = String.format("%04d.%02d.%02d", year, month, day)
//    }
//}
