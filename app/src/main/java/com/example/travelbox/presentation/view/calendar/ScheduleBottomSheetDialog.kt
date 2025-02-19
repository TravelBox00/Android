package com.example.travelbox.presentation.view.calendar

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelbox.databinding.DialogScheduleBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ScheduleBottomSheetDialog(
    private val date: String,
    private val events: List<ScheduleItem>, //  이벤트 데이터 리스트
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

        scheduleAdapter = ScheduleAdapter(events, onDeleteClick)
        binding.recyclerViewSchedules.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewSchedules.adapter = scheduleAdapter

        scheduleAdapter.notifyDataSetChanged()
        Log.d("ScheduleDialog", "✅ RecyclerView 데이터 바인딩 완료!")
        if (events.isEmpty()) {
            Log.w("ScheduleDialog", "🚨 이벤트 데이터가 비어 있음! 다이얼로그 표시 취소")
            return
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