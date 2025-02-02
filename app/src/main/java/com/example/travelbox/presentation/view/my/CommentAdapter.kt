package com.example.travelbox.presentation.view.my.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.travelbox.databinding.ItemCommentBinding

data class Comment(
    val userId: String,
    val commentText: String,
    val profileImageUrl: String? = null,  // 프로필 이미지 URL, 없을 수도 있음
    val myId: String,
    val myCommentText: String,
    val myProfileImageUrl: String? = null
)


class CommentAdapter(
    private val commentList: List<Comment>,
    private val onEdit: (position: Int) -> Unit,
    private val onDelete: (position: Int) -> Unit
) : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    inner class CommentViewHolder(private val binding: ItemCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(comment: Comment, position: Int) {
            // 첫 번째 사용자의 댓글 (user)
            binding.commentUserId.text = comment.userId
            binding.userCommentText.text = comment.commentText
            // 이미지 URL을 로딩하는 경우 (예: Glide, Picasso 사용)
            // Glide.with(binding.root.context).load(comment.profileImageUrl).into(binding.userProfileImage)

            // 두 번째 사용자의 댓글 (my)
            binding.myCommentId.text = comment.myId
            binding.myCommentText.text = comment.myCommentText
            // 이미지 URL을 로딩하는 경우 (예: Glide, Picasso 사용)
            // Glide.with(binding.root.context).load(comment.myProfileImageUrl).into(binding.myProfileImage)

            // 수정 버튼 클릭 시 처리
            binding.commentEdit.setOnClickListener {
                onEdit(position)
            }

            // 삭제 버튼 클릭 시 처리
            binding.commentDelete.setOnClickListener {
                onDelete(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val binding = ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(commentList[position], position)
    }

    override fun getItemCount(): Int = commentList.size
}
