package com.example.travelbox.presentation.view.my

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.travelbox.data.repository.my.ThreadData
import com.example.travelbox.databinding.ItemStoryBinding

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.ImageViewHolder>() {

    private var threadList: MutableList<ThreadData> = mutableListOf()

    inner class ImageViewHolder(private val binding: ItemStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(thread: ThreadData) {
            Glide.with(binding.storyImage.context)
                .load(thread.imageURL)
                .into(binding.storyImage)

            binding.textViewPostContent.visibility = View.GONE
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

    // ✅ updateThreads 함수 추가 (이전 리스트를 지우고 새 리스트를 추가하는 함수)
    fun updateThreads(newList: List<ThreadData>) {
        threadList.clear()
        threadList.addAll(newList)
        notifyDataSetChanged()
    }
}
