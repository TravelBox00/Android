package com.example.travelbox.presentation.view.post

import android.app.Activity
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.travelbox.R
import com.example.travelbox.data.repository.home.HomeRepository
import com.example.travelbox.data.repository.home.HomeRepository.Companion.getPopularPost
import com.example.travelbox.databinding.FragmentHomeBinding
import com.example.travelbox.databinding.FragmentPostBinding
import com.example.travelbox.presentation.view.home.BestPostFragment
import com.example.travelbox.presentation.view.home.DetailPostActivity
import com.example.travelbox.presentation.view.home.FilterActivity
import com.example.travelbox.presentation.view.home.HomeFragment
import com.example.travelbox.presentation.view.home.PostAdapter
import com.example.travelbox.presentation.view.home.PostFilterAdapter
import com.example.travelbox.presentation.viewmodel.PostSharedViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PostFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PostFragment : Fragment() {
    lateinit var binding : FragmentPostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPostBinding.inflate(inflater, container, false)


        // 인기 게시물 조회 함수 호출
        getPopularPost(1, 20)




        // 필터 버튼 클릭
        binding.ivFilter.setOnClickListener {
            val intent = Intent(requireContext(), FilterActivity::class.java)
            startActivityForResult(intent, BestPostFragment.FILTER_REQUEST_CODE)
        }



        // 플로팅 버튼 클릭 시 AddPostFragment로 이동
        binding.btnFloating.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, AddPostFragment())
                .addToBackStack(null)
                .commit()
        }



        return binding.root
    }

    // 이전 프래그먼트로 전환

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == BestPostFragment.FILTER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val city = data?.getStringExtra("city")
            val district = data?.getStringExtra("district")
            if (city != null && district != null) {
                getPopularPostWithFilters(city, district)
            } else {
                Toast.makeText(context, "필터 정보를 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getPopularPostWithFilters(city: String, district: String?) {

        // API 호출로 필터링된 게시물 가져오기
        HomeRepository.regionFilterSearch("여행", district!!, null) { response ->
            if (response?.isSuccess == true) {

                Log.d("지역 필터", "데이터 조회 성공 :$response")
                Toast.makeText(context, "${city} ${district} 필터 적용됨", Toast.LENGTH_SHORT).show()

                // 게시물 어댑터 업데이트
                val filteredPosts = response.result
                val adapter = PostFilterAdapter(filteredPosts)

                adapter.setItemClickListener(object : PostFilterAdapter.onItemClickListener {
                    override fun onItemClick(position: Int) {
                        val selectedItem = filteredPosts[position]
                        val intent = Intent(requireContext(), DetailPostActivity::class.java).apply {
                            putExtra("image", selectedItem.imageURL)
                            putExtra("id", selectedItem.threadId)
                            putExtra("title", selectedItem.postTitle)
                            putExtra("threadId", selectedItem.threadId)
                        }
                        startActivity(intent)
                    }
                })

                binding.recyclerview.adapter = adapter
                binding.recyclerview.layoutManager = GridLayoutManager(requireContext(), 2)

            } else {
                Log.e("지역 필터", "데이터 조회 실패")
            }
        }

    }



    // 인기 게시물 조회
    private fun getPopularPost(page : Int, limit : Int) {
        HomeRepository.getPopularPost(page, limit)
        { result ->

//            if (result != null) {
//                Log.d("BestPostFragment", "데이터 조회 성공 :$result")
            if (result != null && result.size >= 2) {
                Log.d("BestPostFragment", "데이터 조회 성공 :$result")

                val topImages = listOf(result[0].imageURL, result[1].imageURL)
                val sharedViewModel = ViewModelProvider(requireActivity()).get(PostSharedViewModel::class.java)
                sharedViewModel.setTopImages(topImages)

                // 게시물 어댑터 생성
                val adapter = PostAdapter(result)

                adapter.setItemClickListener(object : PostAdapter.onItemClickListener{

                    override fun onItemClick(position: Int) {
                        val selectedItem = result[position]

                        val intent = Intent(requireContext(), DetailPostActivity::class.java).apply {
                            putExtra("image", selectedItem.imageURL)

                            // 닉네임으로 바꿔야 함
                            putExtra("id", selectedItem.userTag)
                            putExtra("title", selectedItem.postContent)
                            putExtra("threadId", selectedItem.threadId)

                            Log.d("BestPostFragment", "보내는 데이터 - Image: ${selectedItem.imageURL}, Id: ${selectedItem.threadId}, Title: ${selectedItem.postContent}, ThreadId: ${selectedItem.threadId}")
                        }


                        startActivity(intent)
                    }
                })


                binding.recyclerview.adapter = adapter
                binding.recyclerview.layoutManager = GridLayoutManager(requireContext(), 2)


                binding.recyclerview.addItemDecoration(object : RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                        outRect.bottom = 50 // 아이템 간의 간격
                        outRect.right= 20
                    }
                })



            } else {
                Log.e("BestPostFragment", "데이터 조회 실패")
            }
        }
    }

    companion object {
        const val FILTER_REQUEST_CODE = 1001
    }




}