package com.example.travelbox.presentation.view.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.signature.ObjectKey
import com.example.travelbox.R
import com.example.travelbox.data.network.ApiNetwork
import com.example.travelbox.data.repository.home.PostData
import com.example.travelbox.data.repository.home.PostItem
import com.example.travelbox.databinding.ItemGridPostBinding


class PostFilterAdapter(private var itemList: List<PostData>) :
    RecyclerView.Adapter<PostFilterAdapter.PostFilterViewHolder>() {



    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    private lateinit var itemClickListener: onItemClickListener

    fun setItemClickListener(itemClickListener: onItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    fun updateData(newList: List<PostData>) {
        itemList = newList
        notifyDataSetChanged()
    }




    override fun onCreateViewHolder(viewgroup: ViewGroup, viewType: Int): PostFilterAdapter.PostFilterViewHolder {


        val binding: ItemGridPostBinding = ItemGridPostBinding.inflate(LayoutInflater.from(viewgroup.context), viewgroup, false)








        return PostFilterViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: PostFilterViewHolder, position: Int) {


        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(position)
        }

        holder.bind(itemList[position])
    }

   

    inner class PostFilterViewHolder(private val binding: ItemGridPostBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: PostData) {



            // 인기 게시물 이미지
            Glide.with(binding.root.context)
                .load(data.imageURL)  // 데이터로 받은 URL
                .load(data.imageURL)
                .thumbnail(Glide.with(binding.root.context).load(data.imageURL).override(100, 100))
                //.transition(DrawableTransitionOptions.withCrossFade())
                //.signature(ObjectKey(System.currentTimeMillis().toString())) // 매번 새로운 이미지로 로딩
                .error(R.drawable.post_ex1)  // 에러 시 표시할 이미지
                .into(binding.imageArea)  // 이미지뷰에 로드

            binding.tvId.text = "@${data.userTag}"
            binding.tvPostTitle.text = data.postTitle


            // 메달 아이콘 표시

            when (position) {
                0 -> {
                    binding.ivMedal.visibility = View.VISIBLE
                    binding.ivMedal.setImageResource(R.drawable.ic_gold_medal) // 금메달
                }

                1 -> {
                    binding.ivMedal.visibility = View.VISIBLE
                    binding.ivMedal.setImageResource(R.drawable.ic_silver_medal) // 은메달
                }

                2 -> {
                    binding.ivMedal.visibility = View.VISIBLE
                    binding.ivMedal.setImageResource(R.drawable.ic_bronze_medal) // 동메달
                }

                else -> {
                    binding.ivMedal.visibility = View.GONE  // 메달 없음
                }


            }

        }


    }


}