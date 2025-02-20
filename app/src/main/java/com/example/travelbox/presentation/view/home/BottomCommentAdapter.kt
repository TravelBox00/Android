package com.example.travelbox.presentation.view.home

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Looper
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
import com.example.travelbox.databinding.ItemCommentBinding
import com.example.travelbox.databinding.ItemGridPostBinding
import com.example.travelbox.databinding.ItemPostCommentBinding
import java.util.logging.Handler

class BottomCommentAdapter(private val itemList: MutableList<CommentRecyclerModel>) :
    RecyclerView.Adapter<BottomCommentAdapter.CommentViewHolder>() {

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    private lateinit var itemClickListener: onItemClickListener

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


            binding.tvCommentId.text = data.commentId
            binding.tvCommentContents.text = data.content


            // 기타 버튼
            etcButton.setOnClickListener {
                showPopup(it)
            }





        }

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
                Toast.makeText(anchorView.context,"수정", Toast.LENGTH_SHORT).show()
                popupWindow.dismiss()
            }


            // 삭제하기 버튼
            popupView.findViewById<TextView>(R.id.tv_delete).setOnClickListener {

                // 리스트에서 해당 댓글 제거
                itemList.removeAt(position)

                // RecyclerView 갱신
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, itemList.size)


                // 삭제 다이얼로그 생성
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






}