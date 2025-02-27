package com.example.travelbox.presentation.view.home

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
import com.example.travelbox.data.network.ApiNetwork
import com.example.travelbox.data.repository.home.HomeRepository
import com.example.travelbox.data.repository.home.PostData
import com.example.travelbox.data.repository.home.PostItem
import com.example.travelbox.databinding.FragmentBestPostBinding
import com.example.travelbox.presentation.view.post.AddPostFragment
import com.example.travelbox.presentation.viewmodel.PostSharedViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BestPostFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BestPostFragment : Fragment() {


    private lateinit var binding : FragmentBestPostBinding

    private var userTag: String? = "jay12"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBestPostBinding.inflate(inflater, container, false)

        //ApiNetwork.init(requireContext())

        // userTag 수신
        userTag = ApiNetwork.getUserTag()


        // 인기 게시물 조회 함수 호출
        getPopularPost(1, 20)


        // 뒤로 가기 버튼 클릭 시
        binding.ivBack.setOnClickListener {
            val homeFragment = HomeFragment() // HomeFragment 객체 생성
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, homeFragment)
                .commit()
        }

        // 필터 버튼 클릭
        binding.ivFilter.setOnClickListener {
            val intent = Intent(requireContext(), FilterActivity::class.java)
            startActivityForResult(intent, FILTER_REQUEST_CODE)
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
        if (requestCode == FILTER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val filtercategory = data?.getStringExtra("category")
            val city = data?.getStringExtra("city")
            val district = data?.getStringExtra("district")
            if (filtercategory!= null && city != null && district != null) {
                getPopularPostWithFilters(filtercategory,city, district)
            } else {
                Toast.makeText(context, "필터 정보를 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getPopularPostWithFilters(category: String, city: String, district: String?) {

        // API 호출로 필터링된 게시물 가져오기
        HomeRepository.regionFilterSearch(category, city, district!!) { response ->
            if (response?.isSuccess == true) {

                Log.d("지역 필터", "데이터 조회 성공 :$response")
                Toast.makeText(context, " ${city} ${district} 게시물", Toast.LENGTH_SHORT).show()

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

                            putExtra("singInfo", selectedItem.singInfo)

                            Log.d("BestPostFragment", "보내는 데이터 - Image: ${selectedItem.imageURL}, Id: ${selectedItem.threadId}, Title: ${selectedItem.postContent}, ThreadId: ${selectedItem.threadId}, SingInfo: ${selectedItem.singInfo}")
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