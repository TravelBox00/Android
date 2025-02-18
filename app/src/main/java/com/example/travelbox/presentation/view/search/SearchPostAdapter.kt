package com.example.travelbox.presentation.view.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.travelbox.R
import com.example.travelbox.data.repository.search.ThreadPost
import com.example.travelbox.databinding.ItemGridPostBinding


class SearchPostAdapter(private val itemList: List<ThreadPost>) :
    RecyclerView.Adapter<SearchPostAdapter.PostViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    private var itemClickListener: OnItemClickListener? = null

    fun setItemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): SearchPostAdapter.PostViewHolder {
        val binding: ItemGridPostBinding =
            ItemGridPostBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return PostViewHolder(binding, itemClickListener)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    inner class PostViewHolder(
        private val binding: ItemGridPostBinding,
        private val itemClickListener: OnItemClickListener?
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                itemClickListener?.onItemClick(adapterPosition)
            }
        }

        fun bind(data: ThreadPost) {
            Glide.with(binding.root.context)
                .load(data.imageUrl)
                .placeholder(R.drawable.post_ex1)
                .error(R.drawable.post_ex1)
                .into(binding.imageArea)

            binding.tvId.text = data.threadId.toString()
            binding.tvPostTitle.text = data.postTitle
        }
    }
}
