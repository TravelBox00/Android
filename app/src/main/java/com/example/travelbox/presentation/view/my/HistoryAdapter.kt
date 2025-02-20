package com.example.travelbox.presentation.view.my

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import com.bumptech.glide.Glide
import com.example.travelbox.data.repository.my.ThreadData
import com.example.travelbox.databinding.ItemStoryBinding

class HistoryAdapter(private val threadList: List<ThreadData>) :
    androidx.recyclerview.widget.RecyclerView.Adapter<HistoryAdapter.ImageViewHolder>() {

    inner class ImageViewHolder(private val binding: ItemStoryBinding) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {

        fun bind(thread: ThreadData) {
            // Glide로 이미지 로드
            Glide.with(binding.storyImage.context)
                .load(thread.imageURL)  // ThreadData에서 imageURL 가져오기
                .into(binding.storyImage)

            // 텍스트를 보이지 않게 설정
            binding.textViewPostContent.visibility = View.GONE // 텍스트를 숨깁니다.
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(threadList[position])
    }

    override fun getItemCount(): Int = threadList.size
}
