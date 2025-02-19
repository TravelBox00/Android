package com.example.travelbox.presentation.view.calendar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.travelbox.databinding.ItemScheduleBinding

class ScheduleAdapter(
    private val schedules: List<ScheduleItem>,
    private val onDeleteClick: (Int) -> Unit
) : RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>() {

    inner class ScheduleViewHolder(private val binding: ItemScheduleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(schedule: ScheduleItem) {
            binding.itemScheduleTitle.text = schedule.title
            binding.itemSchedulePeriod.text = schedule.period
            binding.itemScheduleContent.text = schedule.content

            // 삭제 버튼 클릭 이벤트
            binding.itemScheduleDelete.setOnClickListener {  // ✅ 수정
                onDeleteClick(schedule.travelId)
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

    override fun getItemCount(): Int = schedules.size
}
