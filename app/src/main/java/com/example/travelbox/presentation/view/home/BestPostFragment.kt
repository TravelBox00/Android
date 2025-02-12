package com.example.travelbox.presentation.view.home

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.travelbox.R
import com.example.travelbox.data.network.ApiNetwork
import com.example.travelbox.data.repository.home.HomeRepository
import com.example.travelbox.databinding.FragmentBestPostBinding
import com.example.travelbox.databinding.FragmentHomeBinding

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


    lateinit var binding : FragmentBestPostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBestPostBinding.inflate(inflater, container, false)

        //ApiNetwork.init(requireContext())

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
            startActivity(intent)
        }




        return binding.root



    }



    // 인기 게시물 조회
    private fun getPopularPost(page : Int, limit : Int) {
        HomeRepository.getPopularPost(page, limit)
        { result ->

            if (result != null) {
                Log.d("BestPostFragment", "데이터 조회 성공 :$result")

                // 게시물 어댑터 생성
                val adapter = PostAdapter(result)

                adapter.setItemClickListener(object : PostAdapter.onItemClickListener{

                    override fun onItemClick(position: Int) {
                        val selectedItem = result[position]

                        val intent = Intent(requireContext(), DetailPostActivity::class.java).apply {
                            putExtra("image", selectedItem.imageURL)

                            // 닉네임으로 바꿔야 함
                            putExtra("id", selectedItem.threadId)
                            putExtra("title", selectedItem.postTitle)
                            putExtra("threadId", selectedItem.threadId)
                        }


                        startActivity(intent)
                    }
                })


                binding.recyclerview.adapter = adapter
                binding.recyclerview.layoutManager = GridLayoutManager(requireContext(), 2)


                binding.recyclerview.addItemDecoration(object : RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                        outRect.bottom = 35 // 아이템 간의 간격 35dp
                    }
                })



            } else {
                Log.e("BestPostFragment", "데이터 조회 실패")
            }
        }
    }




}