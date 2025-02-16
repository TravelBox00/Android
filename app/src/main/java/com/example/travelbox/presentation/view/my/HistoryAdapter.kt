package com.example.travelbox.presentation.view.my

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.travelbox.databinding.ItemStoryBinding

class HistoryAdapter (private val imageList: List<String>) : androidx.recyclerview.widget.RecyclerView.Adapter<HistoryAdapter.ImageViewHolder>() {

    inner class ImageViewHolder(private val binding: ItemStoryBinding) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {

        fun bind(imageUrl: String) {
            // Glide로 이미지 로드
            /*
            Glide.with(binding.imageView.context)
                .load(imageUrl)
                .into(binding.imageView)

             */
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(imageList[position])
    }

    override fun getItemCount(): Int = imageList.size
}