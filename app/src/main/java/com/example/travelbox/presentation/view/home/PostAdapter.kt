package com.example.travelbox.presentation.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.travelbox.R
import com.example.travelbox.data.repository.home.PostItem
import com.example.travelbox.databinding.ItemGridPostBinding


class PostAdapter(private val itemList: List<PostItem>) :
        RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

        interface onItemClickListener {
            fun onItemClick(position: Int)
        }

        private lateinit var itemClickListener: onItemClickListener

        fun setItemClickListener(itemClickListener: onItemClickListener) {
            this.itemClickListener = itemClickListener
        }




        override fun onCreateViewHolder(viewgroup: ViewGroup, viewType: Int): PostAdapter.PostViewHolder {


            val binding: ItemGridPostBinding = ItemGridPostBinding.inflate(LayoutInflater.from(viewgroup.context), viewgroup, false)






            return PostViewHolder(binding)
        }

        override fun getItemCount(): Int {
            return itemList.size
        }

        override fun onBindViewHolder(holder: PostViewHolder, position: Int) {


            holder.itemView.setOnClickListener {
                itemClickListener.onItemClick(position)
            }

            holder.bind(itemList[position])
        }


        inner class PostViewHolder(private val binding: ItemGridPostBinding)
            : RecyclerView.ViewHolder(binding.root) {

            fun bind(data: PostItem) {

                // 인기 게시물 이미지
                Glide.with(binding.root.context)
                    .load(data.imageURL)  // 데이터로 받은 URL
                    .placeholder(R.drawable.post_ex1)  // 로딩 중일 때 보여줄 이미지
                    .error(R.drawable.post_ex1)  // 에러 시 표시할 이미지
                    .into(binding.imageArea)  // 이미지뷰에 로드

                binding.tvId.text = data.threadId.toString()
                binding.tvPostTitle.text = data.postTitle



            }

        }



}