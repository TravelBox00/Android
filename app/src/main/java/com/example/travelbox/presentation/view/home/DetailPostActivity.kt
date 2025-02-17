package com.example.travelbox.presentation.view.home

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.travelbox.R
import com.example.travelbox.data.repository.home.HomeRepository
import com.example.travelbox.databinding.ActivityDetailPostBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

class DetailPostActivity : AppCompatActivity() {


    lateinit var binding: ActivityDetailPostBinding
    private  var isLiked = false
    private var isMarked = false

    // threadId 초기화
    private var threadId : Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailPostBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // threadId intent 데이터 수신
        threadId = intent?.getIntExtra("threadId", -1) ?: -1

        // 인텐트로부터 데이터 수신
        val imageResId = intent.getIntExtra("image", R.drawable.post_ex1)  // 기본값 설정
        val id = intent.getStringExtra("id") ?: "No Id"
        val title = intent.getStringExtra("title") ?: "No Title"




        // 데이터 설정
        binding.detailImage.setImageResource(imageResId)
        binding.detailId.text = id
        binding.detailTitle.text = title



        // bottomSheet 적용

        val bottomSheetView = layoutInflater.inflate(R.layout.chat_bottom_sheet, null)
        val bottomSheetDialog = BottomSheetDialog(this, R.style.AppBottomSheetDialogBorder20WhiteTheme)
        bottomSheetDialog.setContentView(bottomSheetView)

        binding.ivChat.setOnClickListener {


            // 바텀시트 내부 TextView 찾기
            val tvDetailId = bottomSheetView.findViewById<TextView>(R.id.tv_bottom_detailId)
            val tvDetailTitle = bottomSheetView.findViewById<TextView>(R.id.tv_bottom_detailTitle)

            // Intent로 받은 데이터 설정
            tvDetailId.text = id
            tvDetailTitle.text = title



            postCommentRecycler(bottomSheetView, threadId)
            bottomSheetDialog.show()

        }

        // 뒤로 가기 버튼

        binding.ivBack.setOnClickListener {
            finish()
        }

        // 좋아요 버튼
        binding.ivHeart.setOnClickListener {
            isLiked = !isLiked

            if (isLiked) {
                binding.ivHeart.setImageResource(R.drawable.ic_heart_on)
            } else {
                binding.ivHeart.setImageResource(R.drawable.ic_heart_off)
            }
        }


        // 북마크 버튼
        binding.ivBookmark.setOnClickListener {
            isMarked = !isMarked

            if (isMarked) {
                binding.ivBookmark.setImageResource(R.drawable.ic_bookmark_on)
            } else {
                binding.ivBookmark.setImageResource(R.drawable.ic_bookmark)
            }

        }



    }



    // 댓글 조회 함수

    private fun postCommentRecycler(bottomSheetView : View, threadId : Int) {
        val recyclerView = bottomSheetView.findViewById<RecyclerView>(R.id.rv_comments)
        recyclerView.layoutManager = LinearLayoutManager(this)


        HomeRepository.getPostComment(threadId) { response ->

            response?.let {

                // 댓글 데이터 리스트 생성
                val itemList = it.result.map { comment ->
                    CommentRecyclerModel(comment.commenterNickname, comment.commentContent)
                }

                // RecyclerView에 데이터 설정
                val adapter = BottomCommentAdapter(itemList.toMutableList())
                recyclerView.adapter = adapter
            } ?: run {
                Log.e("postCommentRecycler", "댓글 데이터를 불러오지 못했습니다.")
            }


        }







    }


}