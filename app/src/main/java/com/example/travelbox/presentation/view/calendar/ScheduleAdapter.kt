package com.example.travelbox.presentation.view.calendar

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.travelbox.databinding.ItemScheduleBinding

class ScheduleAdapter(
    private val schedules: MutableList<ScheduleItem>, // ✅ 리스트를 MutableList로 변경하여 삭제 가능
    private val onDeleteClick: (Int) -> Unit // ✅ 삭제 이벤트 콜백 추가
) : RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>() {

    inner class ScheduleViewHolder(private val binding: ItemScheduleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(schedule: ScheduleItem) {
            binding.itemScheduleTitle.text = schedule.title
            binding.itemSchedulePeriod.text = schedule.period
            binding.itemScheduleContent.text = schedule.content

            Log.d("ScheduleAdapter", "🎯 바인딩: ${schedule.title}, ${schedule.period}, ${schedule.content}")

            binding.itemScheduleDelete.setOnClickListener {
                Log.d("ScheduleAdapter", "🗑️ 삭제 버튼 클릭됨: ${schedule.travelId}")
                onDeleteClick(schedule.travelId) // ✅ 삭제 이벤트 실행
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val binding = ItemScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ScheduleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        holder.bind(schedules[position])
    }

    override fun getItemCount(): Int {
        Log.d("ScheduleAdapter", "✅ 총 아이템 개수: ${schedules.size}")
        return schedules.size
    }
}