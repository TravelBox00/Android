package com.example.travelbox.presentation.view.search

import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.travelbox.R
import com.example.travelbox.databinding.FragmentSearchPostBinding
import com.example.travelbox.presentation.view.home.HomeFragment
import com.example.travelbox.presentation.view.post.AddPostFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchPostFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchPostFragment : Fragment() {

    private lateinit var binding: FragmentSearchPostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchPostBinding.inflate(inflater, container, false)

        // recylcer 함수 호출
        postGridRecycler()

        // 뒤로 가기 버튼 클릭 시
        binding.ivBack.setOnClickListener {
            val homeFragment = HomeFragment() // HomeFragment 객체 생성
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, SearchFragment())
                .commit()
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

    private fun postGridRecycler() {

        val itemList = mutableListOf<SearchRecyclerModel>()

        itemList.add(SearchRecyclerModel(R.drawable.post_ex1, "2025.01.25.", "해유관에서 물고기랑"))
        itemList.add(SearchRecyclerModel(R.drawable.post_ex2, "2025.01.25.", "여우 신사는 처음이지?"))
        itemList.add(SearchRecyclerModel(R.drawable.post_ex1, "2025.01.26.", "물고기랑22"))
        itemList.add(SearchRecyclerModel(R.drawable.post_ex1, "2025.01.27.", "물고기랑33"))
        itemList.add(SearchRecyclerModel(R.drawable.post_ex1, "2025.01.26.", "물고기랑22"))
        itemList.add(SearchRecyclerModel(R.drawable.post_ex1, "2025.01.27.", "물고기랑44"))
        itemList.add(SearchRecyclerModel(R.drawable.post_ex1, "2025.01.26.", "물고기랑55"))
        itemList.add(SearchRecyclerModel(R.drawable.post_ex1, "2025.01.27.", "물고기랑66"))


        val adapter = SearchPostAdapter(itemList)
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = GridLayoutManager(requireContext(), 2)


        binding.recyclerview.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                outRect.bottom = 35 // 아이템 간의 간격 35dp
            }
        })


    }

}