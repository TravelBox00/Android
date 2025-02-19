package com.example.travelbox.presentation.view.home

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.travelbox.R
import com.example.travelbox.data.repository.home.HomeRepository
import com.example.travelbox.databinding.ItemCommentBinding
import com.example.travelbox.databinding.ItemGridPostBinding
import com.example.travelbox.databinding.ItemPostCommentBinding
import java.util.logging.Handler

class BottomCommentAdapter(private val itemList: MutableList<CommentRecyclerModel>) :
    RecyclerView.Adapter<BottomCommentAdapter.CommentViewHolder>() {





    // 댓글 수정 이벤트 인터페이스
//    interface OnCommentActionListener {
//        fun onEditComment(commentId: Int, commentContent: String, commentVisible: String)
//    }

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    private lateinit var itemClickListener: onItemClickListener

    //private var itemClickListener: onItemClickListener ?= null

//    private var editingCommentId: Int? = null
//    private var editingPosition: Int? = null

    fun setItemClickListener(itemClickListener: onItemClickListener) {
        this.itemClickListener = itemClickListener
    }




    override fun onCreateViewHolder(viewgroup: ViewGroup, viewType: Int): BottomCommentAdapter.CommentViewHolder {


        val binding: ItemPostCommentBinding = ItemPostCommentBinding.inflate(LayoutInflater.from(viewgroup.context), viewgroup, false)


        return CommentViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {


        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(position)
        }

        holder.bind(itemList[position])
    }


    inner class CommentViewHolder(private val binding: ItemPostCommentBinding)
        : RecyclerView.ViewHolder(binding.root) {

        private val etcButton : CardView = binding.cvButton


        fun bind(data : CommentRecyclerModel) {


            binding.tvCommentId.text = data.commentNickname
            binding.tvCommentContents.text = data.commentContent

            // 비공개 댓글이면 lock 아이콘 표시
            if (data.commentVisible == "private") {
                binding.ivCommentLock.setImageResource(R.drawable.ic_lock)
            } else {
                binding.ivCommentLock.setImageResource(R.drawable.ic_unlock)
            }


            // 기타 버튼
            etcButton.setOnClickListener {
                showPopup(it)
            }



        }


        // 댓글 팝업 띄우기

        private fun showPopup(anchorView: View) {
            val popupView = LayoutInflater.from(anchorView.context).inflate(R.layout.dialog_comment_etc, null)

            val popupWindow = PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT, true)

            popupWindow.elevation = 10f

            // 배경 클릭 시 팝업 닫기
            popupWindow.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            popupWindow.isOutsideTouchable = true


            // 팝업을 etc 버튼 아래에 표시
            popupWindow.showAsDropDown(anchorView, 0, 10)


            // 수정하기 버튼
            popupView.findViewById<TextView>(R.id.tv_modify).setOnClickListener {
                //Toast.makeText(anchorView.context,"수정", Toast.LENGTH_SHORT).show()

                Toast.makeText(anchorView.context,"수정", Toast.LENGTH_SHORT).show()
                popupWindow.dismiss()

//                commentActionListener.onEditComment(data.commentId, data.commentContent!!, data.commentVisible)
//                popupWindow.dismiss()

            }


            // 삭제하기 버튼
            popupView.findViewById<TextView>(R.id.tv_delete).setOnClickListener {

//                // 리스트에서 해당 댓글 제거
//                itemList.removeAt(position)
//
//                // RecyclerView 갱신
//                notifyItemRemoved(position)
//                notifyItemRangeChanged(position, itemList.size)
//
//
//                // 삭제 다이얼로그 생성
//                val msgDialog = Dialog(anchorView.context)
//                msgDialog.setContentView(R.layout.dialog_msg_delete)
//
//                // 삭제 다이얼로그 크기 설정
//                val params = msgDialog.window?.attributes
//                params?.gravity = Gravity.BOTTOM
//                msgDialog.window?.attributes = params
//                msgDialog.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
//
//
//                // 삭제 다이얼로그 표시
//                msgDialog.show()
//
//                // 2초 후 다이얼로그 닫기
//                android.os.Handler(Looper.getMainLooper()).postDelayed({
//                    msgDialog.dismiss()
//                }, 2000)


                removePostComment(adapterPosition)


                //삭제 다이얼로그 생성
                val msgDialog = Dialog(anchorView.context)
                msgDialog.setContentView(R.layout.dialog_msg_delete)

                // 삭제 다이얼로그 크기 설정
                val params = msgDialog.window?.attributes
                params?.gravity = Gravity.BOTTOM
                msgDialog.window?.attributes = params
                msgDialog.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)


                // 삭제 다이얼로그 표시
                msgDialog.show()

                // 2초 후 다이얼로그 닫기
                android.os.Handler(Looper.getMainLooper()).postDelayed({
                    msgDialog.dismiss()
                }, 2000)

                popupWindow.dismiss()
            }

        }

    }



    private fun removePostComment(position: Int) {

        if (position >= 0 && position < itemList.size) {

            val commentId = itemList[position].commentId

            Log.d("댓글 삭제", "삭제할 댓글 ID: $commentId")


            HomeRepository.postCommentRemove(commentId) { response ->

                Log.d("댓글 삭제", "삭제할 댓글 ID2: $commentId")


                if (response != null && response.isSuccess) {

                    itemList.removeAt(position)
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, itemList.size)







                } else {

                    Log.e("댓글 삭제", "댓글 삭제 실패: ${response?.toString()}")

                }
            }

        } else {
            Log.e("댓글 삭제", "유효하지 않은 댓글 위치입니다. position: $position")
        }


    }

    // 댓글 수정









}