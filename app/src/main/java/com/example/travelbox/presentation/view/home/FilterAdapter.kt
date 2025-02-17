package com.example.travelbox.presentation.view.home

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.travelbox.R
import com.example.travelbox.databinding.ItemFilterBinding

class FilterAdapter(private val items: List<String>, private val onItemClick: (String) -> Unit) :
    RecyclerView.Adapter<FilterAdapter.ViewHolder>() {

    private var selectedPosition = RecyclerView.NO_POSITION  // 선택된 항목 인덱스


//    private var selectedItem: String? = null
//
//
//
//    // 선택된 항목 리턴
//    fun getSelectedItem(): String? {
//        return selectedItem
//    }



    inner class ViewHolder(private val binding: ItemFilterBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: String, position: Int) {
            binding.tvFilter.text = item

            updateSelectionState(position)


            // 아이템 클릭 리스너 설정
            binding.root.setOnClickListener {

                if (selectedPosition != position) {
                    val previousPosition = selectedPosition
                    selectedPosition = position  // 새로 선택된 항목 인덱스 저장

                    // 이전 항목과 새로 선택된 항목을 업데이트
                    notifyItemChanged(previousPosition)
                    notifyItemChanged(selectedPosition)

                    onItemClick(item)

                }
            }

            // 항목에 따른 외곽선 스타일 적용
//            when (position) {
//                0 -> binding.root.setBackgroundResource(R.drawable.style_item_border_noleft)    // 첫 번째 항목
//                items.size - 1 -> binding.root.setBackgroundResource(R.drawable.style_item_border_noright)  // 마지막 항목
//                else -> binding.root.setBackgroundResource(R.drawable.style_item_border)  // 중간 항목
//            }



        }


        private fun updateSelectionState(position: Int) {
            if (position == selectedPosition) {
                // 선택된 항목: 외곽선 제거 + 흰색 배경
                binding.tvFilter.setBackgroundColor(Color.parseColor("#007151"))  // 흰색 배경
                binding.tvFilter.setTextColor(Color.parseColor("#FFFFFF"))
                binding.root.setBackgroundResource(0)  // 외곽선 제거 (drawable 제거)
            } else {
                // 선택되지 않은 항목: 외곽선 적용
                //binding.tvFilter.setBackgroundColor(Color.parseColor("#FFFFFF"))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    override fun getItemCount(): Int = items.size







}