package com.example.travelbox.presentation.view.search

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.travelbox.R
import com.example.travelbox.data.network.ApiNetwork
import com.example.travelbox.data.repository.search.ThreadPost
import com.example.travelbox.databinding.FragmentSearchPostBinding
import com.example.travelbox.presentation.view.home.DetailPostActivity
import com.example.travelbox.presentation.view.post.AddPostFragment
import com.example.travelbox.presentation.viewmodel.PostSharedViewModel

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
    private lateinit var searchPostViewModel: SearchPostViewModel
    private lateinit var postList: List<ThreadPost>
    private var userTag: String? = "testuser1234"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchPostBinding.inflate(inflater, container, false)
        searchPostViewModel = ViewModelProvider(requireActivity()).get(SearchPostViewModel::class.java)

        // userTag 수신
        userTag = ApiNetwork.getUserTag()

        // 뒤로 가기 버튼 클릭 리스너
        binding.ivBack.setOnClickListener {
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

        // ViewModel에서 데이터 가져오기
        searchPostViewModel.posts.observe(viewLifecycleOwner, Observer { posts ->
            if (posts.isNotEmpty()) {
                postList = posts
                setupRecyclerView()
            } else {
                Toast.makeText(requireContext(), "게시물이 없습니다.", Toast.LENGTH_SHORT).show()
            }
        })

        return binding.root

    }

    private fun setupRecyclerView() {
        val topImages = listOf(postList[0].imageURL, postList[1].imageURL)
        val sharedViewModel = ViewModelProvider(requireActivity()).get(PostSharedViewModel::class.java)
        sharedViewModel.setTopImages(topImages)

        searchPostViewModel.posts.observe(viewLifecycleOwner) { posts ->
            Log.d("SearchPostFragment", "ViewModel에서 받은 데이터: $posts")
        }

        val adapter = SearchPostAdapter(postList)

        adapter.setItemClickListener(object :  SearchPostAdapter.OnItemClickListener{

            override fun onItemClick(position: Int) {
                val selectedItem = postList[position]

                val intent = Intent(requireContext(), DetailPostActivity::class.java).apply {
                    putExtra("image", selectedItem.imageURL)

                    putExtra("id", selectedItem.userTag)
                    putExtra("title", selectedItem.postContent)
                    putExtra("threadId", selectedItem.threadId)

                    Log.d("BestPostFragment", "보내는 데이터 - Image: ${selectedItem.imageURL}, Id: ${selectedItem.threadId}, Title: ${selectedItem.postContent}, ThreadId: ${selectedItem.threadId}")
                }


                startActivity(intent)
            }
        })

        binding.recyclerview.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            this.adapter = adapter
        }

        binding.recyclerview.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                outRect.bottom = 50 // 아이템 간의 간격
                outRect.right= 20
            }
        })
    }

}