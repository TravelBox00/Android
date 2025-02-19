package com.example.travelbox.presentation.view.home

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
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
import com.bumptech.glide.Glide
import com.example.travelbox.R
import com.example.travelbox.data.network.ApiNetwork
import com.example.travelbox.data.repository.home.CommentFixRequest
import com.example.travelbox.data.repository.home.HomeRepository
import com.example.travelbox.databinding.ActivityDetailPostBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

class DetailPostActivity : AppCompatActivity() {



    lateinit var binding: ActivityDetailPostBinding
    private  var isLiked = false
    private var isMarked = false

    private var userTag: String? = "jay12"

    private var editingCommentId: Int? = null // 수정 중인 댓글 ID

    // threadId 초기화
    private var threadId : Int = -1
    private var imageResId : String = ""
    private var id : String = ""
    private var title : String = ""

    private lateinit var sharedPreferences: SharedPreferences


    private var commentVisible : String = "public"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailPostBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // threadId intent 데이터 수신
        threadId = intent?.getIntExtra("threadId", -1) ?: -1



        // userTag 수신
        userTag = ApiNetwork.getUserTag()


        // SharedPreferences 초기화
        sharedPreferences = getSharedPreferences("LikePrefs", MODE_PRIVATE)


        // 저장된 좋아요 상태 불러오기
        isLiked = sharedPreferences.getBoolean("isLiked_$threadId", false)
        isMarked = sharedPreferences.getBoolean("isMarked_$threadId", false)


        // 좋아요, 북마크 UI 업데이트
        updateLikeUI()
        updateBookmarkUI()


        // 인텐트로부터 데이터 수신
        imageResId = intent.getStringExtra("image") ?: ""  // 기본값 설정
        id = intent.getStringExtra("id") ?: "No Id"
        title = intent.getStringExtra("title") ?: "No Title"

        Log.d("DetailPostActivity", "받은 데이터 - Image: $imageResId, Id: $id, Title: $title, ThreadId: $threadId")


        // 기존 좋아요 상태 불러오기
        //getLikeStatus(threadId, userTag)



        // 데이터 설정
        //binding.detailImage.setImageResource(imageResId)


        // 사진 불러오기
        Glide.with(this)
            .load(imageResId)
            .placeholder(R.drawable.post_ex1)
            .error(R.drawable.post_ex1)
            .into(binding.detailImage)

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




            // 댓글 불러오기
            postCommentRecycler(bottomSheetView, threadId)

            // 바텀시트
            bottomSheetDialog.show()

        }

        // 뒤로 가기 버튼

        binding.ivBack.setOnClickListener {
            finish()
        }

        // 좋아요 버튼
        binding.ivHeart.setOnClickListener {
//            isLiked = !isLiked
//
//            if (isLiked) {
//                binding.ivHeart.setImageResource(R.drawable.ic_heart_on)
//            } else {
//                binding.ivHeart.setImageResource(R.drawable.ic_heart_off)
//            }

            toggleLike(threadId, userTag!!)


        }


        // 북마크 버튼
        binding.ivBookmark.setOnClickListener {
//            isMarked = !isMarked
//
//            if (isMarked) {
//                binding.ivBookmark.setImageResource(R.drawable.ic_bookmark_on)
//            } else {
//                binding.ivBookmark.setImageResource(R.drawable.ic_bookmark)
//            }

            toggleBookmark(threadId, userTag!!)

        }










    }



    // 댓글 조회 함수

    private fun postCommentRecycler(bottomSheetView : View, threadId : Int) {
        val recyclerView = bottomSheetView.findViewById<RecyclerView>(R.id.rv_comments)
        val etComment = bottomSheetView.findViewById<EditText>(R.id.et_comment)
        val btnSend = bottomSheetView.findViewById<ImageButton>(R.id.btn_send_comment)
        val btnLock = bottomSheetView.findViewById<ImageButton>(R.id.btn_lock)

        recyclerView.layoutManager = LinearLayoutManager(this)


        // 기존 댓글 불러오기
        HomeRepository.getPostComment(threadId) { response ->

            response?.let {



                // 댓글 데이터 리스트 생성
                val itemList = it.result.map { comment ->
                    CommentRecyclerModel(comment.commenterNickname, comment.commentId, comment.commentContent, comment.commentVisible!!)
                }.toMutableList()

                // RecyclerView에 데이터 설정
                val adapter = BottomCommentAdapter(itemList)
                recyclerView.adapter = adapter

                //댓글이 많을 경우, 마지막 댓글 위치로 스크롤
                recyclerView.scrollToPosition(itemList.size - 1)

                // 비공개 댓글 버튼
                btnLock.setOnClickListener {
                    if (commentVisible == "public") {
                        commentVisible = "private"
                        btnLock.setImageResource(R.drawable.ic_lock) // 아이콘 변경
                    } else {
                        commentVisible = "public"
                        btnLock.setImageResource(R.drawable.ic_unlock) // 기본 아이콘
                    }
                }

                // 댓글 전송 버튼 이벤트
                btnSend.setOnClickListener {
                    val commentText = etComment.text.toString().trim()

                    if (commentText.isNotEmpty()) {

                        postAddComment(userTag!!, threadId, commentText, commentVisible, adapter, itemList, etComment, recyclerView)

                    } else {

                        Toast.makeText(this, "댓글을 입력하세요", Toast.LENGTH_SHORT).show()
                    }



//                    if (editingCommentId != null) {
//                        updateComment(editingCommentId!!, commentText, etComment, btnSend)  // 댓글 수정
//                    } else {
//                        postAddComment("userTag", threadId, commentText, commentVisible, adapter, itemList, etComment, recyclerView) // 댓글 추가
//                    }

                }



            } ?: run {
                Log.e("postCommentRecycler", "댓글 데이터를 불러오지 못했습니다.")
            }





        }







    }


    // 댓글 추가 함수
    private fun postAddComment(userTag: String,
                               threadId: Int,
                               commentContent: String,
                               commentVisible: String,
                               adapter: BottomCommentAdapter,
                               itemList: MutableList<CommentRecyclerModel>,
                               etComment: EditText,
                               recyclerView: RecyclerView)
    {

        // 로그로 전달되는 파라미터 확인
        Log.d("댓글 추가", "userTag: $userTag, threadId: $threadId, commentContent: $commentContent, commentVisible: $commentVisible")
        HomeRepository.postCommentAdd(userTag, threadId, commentContent, commentVisible) { response ->

            response?.let {

                val postId = response.result.commentId
                val userId = userTag  // 사용자의 ID (String)



                val newComment = CommentRecyclerModel(commentNickname = userId, commentId = postId, commentContent = commentContent, commentVisible = commentVisible)
                itemList.add(newComment) // 최신 댓글 추가
                adapter.notifyItemInserted(itemList.size - 1)


                // 최신 댓글이 보이도록 자동 스크롤
                recyclerView.postDelayed({
                    recyclerView.scrollToPosition(itemList.size - 1)
                }, 100)


                // 리사이클러뷰 최신 댓글 위치로 스크롤

                //recyclerView.scrollToPosition(itemList.size - 1)

                // 입력창 초기화
                etComment.text.clear() // 입력창 초기화
            } ?: run {
                Toast.makeText(this, "댓글 등록 실패", Toast.LENGTH_SHORT).show()
            }

        }
    }



//    // 댓글 수정 API 호출
//    private fun updateComment(commentId: Int, newContent: String, etComment: EditText, btnSend: ImageButton) {
//        val request = CommentFixRequest(commentId, newContent, commentVisible)
//
//        HomeRepository.postCommentFix(request) { response ->
//            if (response?.isSuccess == true) {
//                Toast.makeText(this, "댓글이 수정되었습니다.", Toast.LENGTH_SHORT).show()
//
//                // 수정 후 초기화
//                editingCommentId = null
//                etComment.text.clear()
//                btnSend.setImageResource(R.drawable.ic_send)
//
//                // 댓글 목록 다시 불러오기
//                postCommentRecycler(findViewById(R.id.bottomSheetDashBoardLayout), threadId)
//            } else {
//                Toast.makeText(this, "댓글 수정 실패", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }




    // 1. 좋아요 상태 확인 (초기 로딩 시 호출)
//    private fun getLikeStatus(threadId: Int, userTag: String) {
//        HomeRepository.postAddIsLiked(threadId, userTag) { response ->
//            response?.let {
//                isLiked = it.result.isLiked // API 응답값으로 업데이트
//                updateLikeUI()
//            } ?: run {
//                Log.e("게시물 좋아요", "좋아요 상태 불러오기 실패")
//            }
//        }
//    }

    // 좋아요 버튼 클릭 시 상태 변경
    private fun toggleLike(threadId: Int, userTag: String) {
        HomeRepository.postAddIsLiked(threadId, userTag) { response ->
            response?.let {
                isLiked = it.result.isLiked // API 응답값으로 업데이트
                updateLikeUI()

                // 좋아요 상태를 SharedPreferences에 저장
                sharedPreferences.edit().putBoolean("isLiked_$threadId", isLiked).apply()

            } ?: run {
                Toast.makeText(this, "좋아요 처리 실패", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // 좋아요 UI 업데이트 함수
    private fun updateLikeUI() {
        if (isLiked) {
            binding.ivHeart.setImageResource(R.drawable.ic_heart_on)
        } else {
            binding.ivHeart.setImageResource(R.drawable.ic_heart_off)
        }
    }

    private fun toggleBookmark(threadId: Int, userTag: String) {
        HomeRepository.postAddIsScrapped(threadId, userTag) { response ->
            response?.let {
                isMarked = it.result.isScrapped  // API 응답값에 따라 북마크 상태 변경
                updateBookmarkUI()

                // 북마크 상태 저장
                sharedPreferences.edit().putBoolean("isMarked_$threadId", isMarked).apply()

            } ?: run {
                Toast.makeText(this, "북마크 처리 실패", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun updateBookmarkUI() {
        if (isMarked) {
            binding.ivBookmark.setImageResource(R.drawable.ic_bookmark_on)
        } else {
            binding.ivBookmark.setImageResource(R.drawable.ic_bookmark)
        }
    }


    // 댓글 수정








}