package com.example.travelbox.presentation.view.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.signature.ObjectKey
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
                .load(data.imageURL)
                .load(data.imageURL)
                .thumbnail(Glide.with(binding.root.context).load(data.imageURL).override(100, 100))
                .transition(DrawableTransitionOptions.withCrossFade())
                .signature(ObjectKey(System.currentTimeMillis().toString())) // 매번 새로운 이미지로 로딩
                .error(R.drawable.post_ex1)  // 에러 시 표시할 이미지
                .into(binding.imageArea)

            binding.tvId.text = "@${data.userTag}"
            binding.tvPostTitle.text = data.postContent
        }
    }
}
