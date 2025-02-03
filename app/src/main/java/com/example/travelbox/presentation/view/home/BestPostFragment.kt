package com.example.travelbox.presentation.view.home

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.text.TextUtils.replace
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.travelbox.R
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


        // recylcer 함수 호출
        postGridRecycler()


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


    private fun postGridRecycler() {

        val itemList = mutableListOf<PostRecyclerModel>()

        itemList.add(PostRecyclerModel(R.drawable.post_ex1, "@way1234", "해유관에서 물고기랑" ))
        itemList.add(PostRecyclerModel(R.drawable.post_ex2, "@way1234", "여우 신사는 처음이지?" ))
        itemList.add(PostRecyclerModel(R.drawable.post_ex1, "@jopeng1234", "물고기랑22" ))
        itemList.add(PostRecyclerModel(R.drawable.post_ex1, "@jopeng1234", "물고기랑33" ))
        itemList.add(PostRecyclerModel(R.drawable.post_ex1, "@jopeng1234", "물고기랑22" ))
        itemList.add(PostRecyclerModel(R.drawable.post_ex1, "@w1nner", "물고기랑44" ))
        itemList.add(PostRecyclerModel(R.drawable.post_ex1, "@w1nner", "물고기랑55" ))
        itemList.add(PostRecyclerModel(R.drawable.post_ex1, "@w1nner", "물고기랑66" ))


        val adapter = PostAdapter(itemList)

        // 클릭 리스너 설정
        adapter.setItemClickListener(object : PostAdapter.onItemClickListener{

            override fun onItemClick(position: Int) {
                val selectedItem = itemList[position]

                val intent = Intent(requireContext(), DetailPostActivity::class.java).apply {
                    putExtra("image", selectedItem.image)
                    putExtra("id", selectedItem.id)
                    putExtra("title", selectedItem.title)
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
    }


}