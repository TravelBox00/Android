package com.example.travelbox.presentation.view.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.travelbox.R
import com.example.travelbox.databinding.ItemGridPostBinding
import kotlinx.coroutines.NonDisposableHandle.parent

class PostAdapter(private val itemList: MutableList<PostRecyclerModel> ) :
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

            fun bind(data : PostRecyclerModel) {


                binding.imageArea.setImageResource(data.image)
                binding.tvId.text = data.id
                binding.tvPostTitle.text = data.title







            }

        }



}