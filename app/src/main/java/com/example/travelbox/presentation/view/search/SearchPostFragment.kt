package com.example.travelbox.presentation.view.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelbox.R
import com.example.travelbox.data.repository.search.ThreadPost
import com.example.travelbox.databinding.FragmentSearchPostBinding
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
    private lateinit var searchPostViewModel: SearchPostViewModel
    private lateinit var postList: List<ThreadPost>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchPostBinding.inflate(inflater, container, false)
        searchPostViewModel = ViewModelProvider(requireActivity()).get(SearchPostViewModel::class.java)

        // 뒤로 가기 버튼 클릭 시
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
        val adapter = SearchPostAdapter(postList)
        binding.recyclerview.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }
    }

}