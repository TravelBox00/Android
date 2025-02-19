package com.example.travelbox.presentation.view.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.travelbox.R
import com.example.travelbox.data.repository.home.HomeRepository
import com.example.travelbox.data.repository.home.PostData
import com.example.travelbox.data.repository.home.PostItem
import com.example.travelbox.databinding.FragmentCategoryPostBinding

class CategoryPostFragment(private val category: String) : Fragment() {

    private lateinit var binding: FragmentCategoryPostBinding
    private lateinit var postAdapter: PostFilterAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryPostBinding.inflate(inflater, container, false)

        postAdapter = PostFilterAdapter(listOf())
        val recyclerView = binding.recyclerview
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = postAdapter

        loadCategoryPosts(category)





        return binding.root
    }

    private fun loadCategoryPosts(category: String) {
        HomeRepository.regionFilterSearch(category, null, null) { response ->
            if (response?.isSuccess == true ) {

                response.result.forEach { post ->
                    Log.d("CategoryPostFragment", "Image URL: ${post.imageURL}")
                }

                //val mappedPosts = mapPostDataToPostItem(response.result)
                //postAdapter.updateData(mappedPosts)
                postAdapter.updateData(response.result)

                // 게시물 어댑터 업데이트
                val filteredPosts = response.result
                val adapter = PostFilterAdapter(filteredPosts)

                adapter.setItemClickListener(object : PostFilterAdapter.onItemClickListener {
                    override fun onItemClick(position: Int) {
                        val selectedItem = filteredPosts[position]
                        val intent = Intent(requireContext(), DetailPostActivity::class.java).apply {
                            putExtra("image", selectedItem.imageURL)
                            putExtra("id", selectedItem.userTag)
                            putExtra("title", selectedItem.postTitle)
                            putExtra("threadId", selectedItem.threadId)

                            Log.d("BestPostFragment", "보내는 데이터 - Image: ${selectedItem.imageURL}, Id: ${selectedItem.userTag}, Title: ${selectedItem.postTitle}, ThreadId: ${selectedItem.threadId}")

                        }
                        startActivity(intent)
                    }
                })

                binding.recyclerview.adapter = adapter
                binding.recyclerview.layoutManager = GridLayoutManager(requireContext(), 2)





            } else {
                Log.e("CategoryPostFragment", "$category 게시물 조회 실패")
            }
        }
    }

//    private fun mapPostDataToPostItem(postDataList: List<PostData>): List<PostItem> {
//        return postDataList.map { postData ->
//            PostItem(
//                threadId = postData.threadId,
//                postTitle = postData.postTitle,
//                postDate = postData.postDate,
//                //imageURL = postData.postImageURL ?: "",
//                //totalEngagement = null
//            )
//        }
//    }
}