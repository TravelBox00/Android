package com.example.travelbox.presentation.view.calendar

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelbox.data.repository.calendar.CalendarRepository
import com.example.travelbox.databinding.DialogScheduleBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ScheduleBottomSheetDialog(
    private val date: String,
    private var events: MutableList<ScheduleItem>, // ✅ MutableList로 변경하여 삭제 후 리스트 갱신 가능
    private val onDeleteClick: (Int) -> Unit
) : BottomSheetDialogFragment() {

    private var _binding: DialogScheduleBottomSheetBinding? = null
    private val binding get() = _binding!!

    private lateinit var scheduleAdapter: ScheduleAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = DialogScheduleBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (_binding == null) {
            Log.e("ScheduleDialog", "🚨 Binding이 NULL 상태! 다이얼로그 로딩 중단")
            return
        }

        Log.d("ScheduleDialog", "✅ 다이얼로그 onViewCreated 실행됨! 🎉")
        Log.d("ScheduleDialog", "✅ 받은 일정 개수: ${events.size}")

        binding.dialogDate.text = date

        scheduleAdapter = ScheduleAdapter(events) { travelId ->
            deleteSchedule(travelId) // ✅ 삭제 요청 실행
        }

        binding.recyclerViewSchedules.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewSchedules.adapter = scheduleAdapter
    }

    /**
     * 🗑️ 일정 삭제 메서드 (삭제 후 UI 업데이트)
     */
    @SuppressLint("NotifyDataSetChanged")
    private fun deleteSchedule(travelId: Int) {
        Log.d("ScheduleDialog", "🗑️ 삭제 요청 시작: travelId=$travelId")

        CalendarRepository.deleteCalendarEvent(travelId) { success, message ->
            requireActivity().runOnUiThread {
                if (success) {
                    Log.d("ScheduleDialog", "✅ 일정 삭제 성공 → UI 갱신 및 다이얼로그 닫기")

                    // ✅ 리스트에서 삭제된 아이템 제거
                    val updatedList = events.filter { it.travelId != travelId }.toMutableList()
                    events.clear()
                    events.addAll(updatedList)

                    // ✅ UI 즉시 갱신
                    scheduleAdapter.notifyDataSetChanged()

                    // ✅ 삭제 후 캘린더에 일정 갱신 요청
                    parentFragmentManager.setFragmentResult("calendar_update", Bundle())

                    // ✅ 삭제 후 리스트가 비어 있으면 다이얼로그 닫기
                    if (events.isEmpty()) {
                        dismissAllowingStateLoss() // 안전하게 다이얼로그 닫기
                    }

                } else {
                    Log.e("ScheduleDialog", "❌ 일정 삭제 실패: $message")
                    Toast.makeText(requireContext(), "삭제 실패: $message", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    override fun onStart() {
        super.onStart()
        dialog?.window?.setDimAmount(0f) // 🔥 어두운 배경 효과 제거
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}