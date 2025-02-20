package com.example.travelbox.presentation.view.calendar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.travelbox.R
import com.example.travelbox.databinding.ItemPostBinding

data class PostItem(
    val imageURL: String,
    val postContent: String,
    val postDate: String // 추가됨 ✅
)

class PostAdapter(private val postList: List<PostItem>) :
    RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val postImage: ImageView = itemView.findViewById(R.id.post_image)
        val postContent: TextView = itemView.findViewById(R.id.post_content)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = postList[position]

        // ✅ 올바른 변수명 사용
        holder.postContent.text = post.postContent
        Glide.with(holder.itemView.context)
            .load(post.imageURL)
            .into(holder.postImage)
    }

    override fun getItemCount(): Int = postList.size
}
