package com.example.travelbox.presentation.view.my.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.travelbox.data.repository.my.Comment
import com.example.travelbox.databinding.ItemCommentBinding


class CommentAdapter(
    private val commentList: MutableList<Comment>,  // MutableList로 변경
    private val onEdit: (position: Int) -> Unit,
    private val onDelete: (position: Int) -> Unit
) : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    inner class CommentViewHolder(private val binding: ItemCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(comment: Comment, position: Int) {
            binding.commentUserId.text = comment.postOwnerNickname
            binding.userCommentText.text = comment.commentContent
            // 프로필 이미지 URL 로딩 (예: Glide 사용)
            // Glide.with(binding.root.context).load(comment.profileImageUrl).into(binding.userProfileImage)

            binding.myCommentId.text = comment.postOwnerNickname
            binding.myCommentText.text = comment.postContent
            // 프로필 이미지 URL 로딩 (예: Glide 사용)
            // Glide.with(binding.root.context).load(comment.myProfileImageUrl).into(binding.myProfileImage)

            // 수정 버튼 클릭 시 처리
            binding.commentEdit.setOnClickListener { onEdit(position) }
            // 삭제 버튼 클릭 시 처리
            binding.commentDelete.setOnClickListener { onDelete(position) }
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

    // 새 데이터를 추가하는 함수
    fun updateComments(newComments: List<Comment>) {
        commentList.addAll(newComments)  // 새 댓글 추가
        notifyDataSetChanged()  // RecyclerView 갱신
    }
}
