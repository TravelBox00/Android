package com.example.travelbox.presentation.view.calendar

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelbox.databinding.DialogScheduleBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class ScheduleBottomSheetDialog(
    context: Context,
    private val date: String,
    private val events: List<ScheduleItem>,
    private val onDeleteClick: (Int) -> Unit
) : BottomSheetDialog(context) {

    private lateinit var binding: DialogScheduleBottomSheetBinding
    private lateinit var scheduleAdapter: ScheduleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("ScheduleDialog", "✅ 다이얼로그 onCreate 실행됨!")

        binding = DialogScheduleBottomSheetBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)

        Log.d("ScheduleDialog", "✅ 받은 일정 개수: ${events.size}")
        events.forEach { Log.d("ScheduleDialog", "✅ 일정: ${it.title}, ${it.period}") }

        binding.dialogDate.text = date

        // RecyclerView 설정
        scheduleAdapter = ScheduleAdapter(events, onDeleteClick)
        binding.recyclerViewSchedules.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewSchedules.adapter = scheduleAdapter
        scheduleAdapter.notifyDataSetChanged()
    }
}

