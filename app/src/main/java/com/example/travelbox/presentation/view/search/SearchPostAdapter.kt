package com.example.travelbox.presentation.view.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.travelbox.databinding.ItemGridPostBinding


class SearchPostAdapter(private val itemList: MutableList<SearchRecyclerModel>) :
    RecyclerView.Adapter<SearchPostAdapter.PostViewHolder>() {

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    private lateinit var itemClickListener: onItemClickListener

    fun setItemClickListener(itemClickListener: onItemClickListener) {
        this.itemClickListener = itemClickListener
    }


    override fun onCreateViewHolder(
        viewgroup: ViewGroup,
        viewType: Int
    ): SearchPostAdapter.PostViewHolder {

        val binding: ItemGridPostBinding =
            ItemGridPostBinding.inflate(LayoutInflater.from(viewgroup.context), viewgroup, false)

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


    inner class PostViewHolder(private val binding: ItemGridPostBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: SearchRecyclerModel) {
            binding.imageArea.setImageResource(data.image)
            binding.tvDate.text = data.date
            binding.tvPostTitle.text = data.title
        }

    }


}